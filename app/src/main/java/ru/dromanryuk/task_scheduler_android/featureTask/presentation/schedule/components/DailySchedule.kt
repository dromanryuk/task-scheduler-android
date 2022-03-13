package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.TaskViewState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
fun Schedule(
    tasks: List<TaskViewState>,
    modifier: Modifier = Modifier,
    onTaskClick: (String) -> Unit,
    eventContent: @Composable (state: TaskViewState) -> Unit = { TaskItem(task = it, onClick = { onTaskClick(it.id) }) },
) {
    val hourHeight = 64.dp
    val configuration = LocalConfiguration.current
    val dayWidth = configuration.screenWidthDp.dp
    val dividerColor = MaterialTheme.colors.primary
    Layout(
        content = {
            tasks.sortedBy(TaskViewState::date).forEach { task ->
                Box(modifier = Modifier.eventData(task)) {
                    eventContent(task)
                }
            }
        },
        modifier = modifier
            .drawBehind {
                repeat(23) {
                    drawLine(
                        dividerColor,
                        start = Offset(0f, (it + 1) * hourHeight.toPx()),
                        end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                repeat(1) {
                    drawLine(
                        dividerColor,
                        start = Offset(15f, 0f),
                        end = Offset(15f, 24 * hourHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            },
    ) { measureables, constraints ->
        val height = hourHeight.roundToPx() * 24
        val placeablesWithTasks = measureables.map { measurable ->
            val task = measurable.parentData as TaskViewState
            val taskDurationMinutes = ChronoUnit.MINUTES.between(task.date, task.date.plusHours(1))
            val taskHeight = ((taskDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val placeable = measurable.measure(constraints.copy(minHeight = taskHeight, maxHeight = taskHeight))
            Pair(placeable, task)
        }
        layout(constraints.maxWidth, height) {
            var y = 0
            placeablesWithTasks.forEach { (placeable, task) ->
                val taskOffsetMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, task.date.toLocalTime())
                val taskY = ((taskOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                placeable.place(0, taskY)
            }
        }
    }
}

private class EventDataModifier(
    val event: TaskViewState,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = event
}

private fun Modifier.eventData(state: TaskViewState) = this.then(EventDataModifier(state))

@Composable
fun TaskItem(task: TaskViewState, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 7.dp, end = 10.dp)
            .background(MaterialTheme.colors.secondary, shape = RoundedCornerShape(4.dp))
            .clickable { onClick() }
    ) {
        Text(
            modifier = modifier.padding(start = 5.dp, end = 5.dp),
            text = EventTimeFormatter.format(task.date),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.background
        )
        Text(
            modifier = modifier.padding(start = 5.dp, end = 5.dp),
            text = task.title,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.background
        )
        Text(
            modifier = modifier.padding(start = 5.dp, end = 5.dp),
            text = task.description,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.background
        )
    }
}

@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) },
) {
    Column(modifier = modifier) {
        val startTime = LocalTime.MIN
        repeat(24) { i ->
            Box(modifier = Modifier.height(hourHeight)) {
                label(startTime.plusHours(i.toLong()))
            }
        }
    }
}

@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier,
) {
    Text(
        text = time.format(EventTimeFormatter),
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
    )
}

val EventTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")