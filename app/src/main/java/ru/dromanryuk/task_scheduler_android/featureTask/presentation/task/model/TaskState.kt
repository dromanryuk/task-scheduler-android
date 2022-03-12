package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model

data class TaskState(
    val titleState: String = "",
    val descriptionState: String = "",
    val dateTimePikersState: DateTimePickersState = DateTimePickersState(),
    val isExitFromScreen: Boolean = false
)