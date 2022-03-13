package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.viewModel
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultFloatingActionButton
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultScaffold
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.showDatePicker
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.showTimePicker
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.components.RemoveTaskDialog
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.components.TaskScreenContent
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.components.TaskTopAppBar
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.DropdownType.*
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.TaskEditingAction
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.TaskState

@ExperimentalComposeUiApi
@Composable
fun TaskScreen(
    taskId: String,
    navigateBack: () -> Unit
) {
    val viewModel by viewModel<TaskViewModel>()
    val state by viewModel.state.collectAsState()
    val sendAction = viewModel::sendAction

    SideEffect {
        if (state.id != taskId) {
            sendAction(TaskEditingAction.LoadTask(taskId))
        }
    }

    DefaultScaffold(
        modifier = Modifier,
        topBar = {
            TaskTopAppBar(
                onNavigateBack = { sendAction(TaskEditingAction.ExitScreen) },
                onDeleteTask = { sendAction(TaskEditingAction.UpdateRemoveTaskDialogVisibility(true)) }
            )
        },
        bottomBar = {  },
        content = {
            TaskScreenContent(
                task = state,
                onTitleChange = { sendAction(TaskEditingAction.OnTitleChanged(it)) },
                onContentChange = { sendAction(TaskEditingAction.OnDescriptionChanged(it)) },
                onDateTimeChange = { sendAction(TaskEditingAction.UpdateDateTimeDialogVisibility(it, true)) },
            )
        },
        floatingActionButton = {
            DefaultFloatingActionButton(
                onClick = { sendAction(TaskEditingAction.SaveEditing) },
                icon = Icons.Outlined.Check
            )
        }
    )
    DateDialogWrapper(state, sendAction)
    TimeDialogWrapper(state, sendAction)
    RemoveTaskDialogWrapper(state, sendAction)
    LaunchedEffect(key1 = state.isExitFromScreen) {
        if (state.isExitFromScreen)
            navigateBack()
    }
}

@Composable
private fun DateDialogWrapper(
    state: TaskState,
    sendAction: (event: TaskEditingAction) -> Unit
) {
    if (state.dateTimePikersState.dateDialogVisibility) {
        showDatePicker(
            activity = LocalContext.current as AppCompatActivity,
            updateDate = {
                sendAction(TaskEditingAction.UpdateDateTime(it))
            },
            onCancel = { sendAction(TaskEditingAction.UpdateDateTimeDialogVisibility(DATE, false)) }
        )
    }
}

@Composable
private fun TimeDialogWrapper(
    state: TaskState,
    sendAction: (event: TaskEditingAction) -> Unit
) {
    if (state.dateTimePikersState.timeDialogVisibility) {
        showTimePicker(
            activity = LocalContext.current as AppCompatActivity,
            currDate = state.dateTimePikersState.currDateTime,
            updateTime = { sendAction(TaskEditingAction.UpdateDateTime(it)) },
            onCancel ={ sendAction(TaskEditingAction.UpdateDateTimeDialogVisibility(TIME, false)) }
        )
    }
}

@Composable
private fun RemoveTaskDialogWrapper(
    state: TaskState,
    sendAction: (TaskEditingAction) -> Unit,
) {
    if (state.removeTaskDialogVisibility)
        RemoveTaskDialog(
            onDismiss = { sendAction(TaskEditingAction.UpdateRemoveTaskDialogVisibility(false)) },
            onRemoveTask = { sendAction(TaskEditingAction.RemoveTask) }
        )
}