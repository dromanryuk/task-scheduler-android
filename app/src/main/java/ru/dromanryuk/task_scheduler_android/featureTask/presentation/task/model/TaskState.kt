package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model

data class TaskState(
    val id: String = "",
    val titleState: String = "",
    val descriptionState: String = "",
    val removeTaskDialogVisibility: Boolean = false,
    val dateTimePikersState: DateTimePickersState = DateTimePickersState(),
    val isExitFromScreen: Boolean = false
)