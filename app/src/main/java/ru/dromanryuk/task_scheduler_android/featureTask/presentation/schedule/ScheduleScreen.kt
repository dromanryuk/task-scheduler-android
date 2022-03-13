package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.viewModel
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultFloatingActionButton
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultScaffold
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.showDatePicker
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.ScheduleScreenContent
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.ScheduleTopAppBar
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleAction
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleState

@Composable
fun ScheduleScreen(navigateToTaskScreen: (String) -> Unit) {
    val viewModel by viewModel<ScheduleViewModel>()
    val state by viewModel.state.collectAsState()
    val sendAction = viewModel::sendAction

    LaunchedEffect(state.tasksViewStates) {
        sendAction(ScheduleAction.LoadTasks)
    }

    DefaultScaffold(
        modifier = Modifier,
        topBar = {
            ScheduleTopAppBar(
                state = state,
                onDateEditClick = { sendAction(ScheduleAction.UpdateDateTimeDialogVisibility(true)) },
                navigateToCurrentDay = { sendAction(ScheduleAction.ChangeToCurrentDay) }
            )
        },
        bottomBar = {  },
        content = {
            ScheduleScreenContent(
                state = state,
                onTaskClick = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            DefaultFloatingActionButton(
                onClick = {
                    sendAction(ScheduleAction.CreateTask)
                },
                icon = Icons.Filled.Add
            )
        }
    )
    DateDialogWrapper(state, sendAction)
    LaunchedEffect(key1 = state.createdTaskId) {
        state.createdTaskId?.let {
            navigateToTaskScreen(it)
            sendAction(ScheduleAction.ClearCreatedTaskId)
        }
    }
}

@Composable
private fun DateDialogWrapper(state: ScheduleState, sendAction: (event: ScheduleAction) -> Unit) {
    if (state.dateDialogVisibility) {
        showDatePicker(
            activity = LocalContext.current as AppCompatActivity,
            updateDate = { sendAction(ScheduleAction.UpdateDate(it)) },
            onCancel = { sendAction(ScheduleAction.UpdateDateTimeDialogVisibility(false)) }
        )
    }
}