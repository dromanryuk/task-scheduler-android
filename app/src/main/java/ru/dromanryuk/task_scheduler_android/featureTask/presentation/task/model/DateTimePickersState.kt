package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model

import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.DropdownType.*
import java.time.LocalDateTime

data class DateTimePickersState(
    val currDateTime: LocalDateTime = LocalDateTime.now(),
    val dateDialogVisibility: Boolean = false,
    val timeDialogVisibility: Boolean = false,
)

fun DateTimePickersState.changeDialogVisibility(type: DropdownType, newVal: Boolean) =
    when (type) {
        DATE -> copy(dateDialogVisibility = newVal)
        TIME -> copy(timeDialogVisibility = newVal)
    }

enum class DropdownType {
    DATE, TIME
}
