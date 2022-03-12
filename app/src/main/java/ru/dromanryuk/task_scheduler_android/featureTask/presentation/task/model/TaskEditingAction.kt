package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model

import java.time.LocalDateTime

sealed class TaskEditingAction {
    data class OnTitleChanged(val title: String) : TaskEditingAction()
    data class OnDescriptionChanged(val description: String) : TaskEditingAction()

    data class UpdateDateTimeDialogVisibility(val type: DropdownType, val newVal: Boolean) : TaskEditingAction()

    data class UpdateDateTime(val dateTime: LocalDateTime) : TaskEditingAction()

    object SaveEditing : TaskEditingAction()

    object ExitScreen: TaskEditingAction()
}
