package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleState
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.TaskViewState
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.getCurrDayOfWeekStr
import java.time.LocalDateTime

@Composable
fun ScheduleScreenContent(state: ScheduleState, onTaskClick: (String) -> Unit) {
    val verticalScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AllTasksPanel(state, onTaskClick)
        Row(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(verticalScrollState)
        ) {
            ScheduleSidebar(
                hourHeight = 64.dp
            )
            Schedule(
                tasks = state.tasksViewStates,
                onTaskClick = onTaskClick
            )
        }
    }
}

@Composable
fun AllTasksPanel(state: ScheduleState, onTaskClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DayIcon(state.selectedDate, state.isCurrDay)
        AllTodayTasks(state.tasksViewStates, onTaskClick)
    }
}

@Composable
fun DayIcon(selectedDate: LocalDateTime, isCurrDay: Boolean) {
    val circleColor = MaterialTheme.colors.secondary
    Column(
        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = getCurrDayOfWeekStr(selectedDate),
            style = MaterialTheme.typography.caption,
            color = if (isCurrDay) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isCurrDay) {
                Canvas(
                    modifier = Modifier
                        .size(40.dp),
                ) {
                    drawCircle(color = circleColor)
                }
            }
            Text(
                text = selectedDate.dayOfMonth.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = if (isCurrDay) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )
        }
    }
}

@Composable
fun AllTodayTasks(tasks: List<TaskViewState>, onTaskClick: (String) -> Unit) {
    FlowRow(
        modifier = Modifier.padding(bottom = 10.dp, end = 15.dp),
        mainAxisSpacing = 5.dp,
        crossAxisSpacing = 5.dp
    ) {
        tasks.forEach {
            TaskItem(title = it.title, id = it.id, onTaskClick)
        }
    }
}

@Composable
fun TaskItem(title: String, id: String, onTaskClick: (String) -> Unit) {
    Text(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .clickable { onTaskClick(id) },
        text = title,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.body2
    )
}