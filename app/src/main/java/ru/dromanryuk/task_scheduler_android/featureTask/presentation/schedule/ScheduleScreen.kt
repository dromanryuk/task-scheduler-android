package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.viewModel
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultScaffold
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.showDatePicker
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.ScheduleFloatingActionButton
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.ScheduleScreenContent
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.ScheduleTopAppBar
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleAction
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleState

@Composable
fun ScheduleScreen() {
    val viewModel by viewModel<ScheduleViewModel>()
    val state by viewModel.state.collectAsState()
    val sendAction = viewModel::sendAction

    Log.d("tasks", "state.tasksViewStates === ${state.tasksViewStates}")

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
                state = state
            )
        },
        floatingActionButton = {
            ScheduleFloatingActionButton(
                onAddClick = { sendAction(ScheduleAction.CreateTask) }
            )
        }
    )
    DateDialogWrapper(state, sendAction)
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