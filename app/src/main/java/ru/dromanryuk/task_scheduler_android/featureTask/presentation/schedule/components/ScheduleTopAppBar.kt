package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleState
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.getCurrDateStr
import ru.dromanryuk.task_scheduler_android.ui.theme.SchedulerTheme

@Composable
fun ScheduleTopAppBar(
    state: ScheduleState,
    onDateEditClick: () -> Unit,
    navigateToCurrentDay: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onDateEditClick() }
                    )
                    .background(MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = getCurrDateStr(state.selectedDate),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.surface
                )
                IconButton(
                    onClick = { onDateEditClick() },
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
            IconButton(
                onClick = { navigateToCurrentDay() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewScheduleTopAppBar() {
    SchedulerTheme {
        ScheduleTopAppBar(ScheduleState(), {}, {})
    }
}