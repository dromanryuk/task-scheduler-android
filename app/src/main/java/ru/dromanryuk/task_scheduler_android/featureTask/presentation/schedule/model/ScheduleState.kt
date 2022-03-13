package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ScheduleState(
    val tasksViewStates: List<TaskViewState> = emptyList(),
    val createdTaskId: String? = null,
    val dateDialogVisibility: Boolean = false,
    val selectedDate: LocalDateTime = LocalDateTime.now(),
    val isCurrDay: Boolean = isCurrDay(selectedDate),
)

fun isCurrDay(date: LocalDateTime): Boolean {
    return date.toLocalDate() == LocalDate.now()
}

fun getCurrDateStr(currDateTime: LocalDateTime): String {
    val dateFormat = DateTimeFormatter.ofPattern("LLL")
    val yearFormat = DateTimeFormatter.ofPattern("LLL yyyy")
    return if (currDateTime.year == LocalDateTime.now().year) {
        getFormatString(currDateTime, dateFormat)
    } else {
        getFormatString(currDateTime, yearFormat)
    }
}

fun getFormatString(currDateTime: LocalDateTime, yearFormat: DateTimeFormatter): String {
    return yearFormat.format(currDateTime)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun getCurrDayOfWeekStr(currDateTime: LocalDateTime): String {
    val dateFormat = DateTimeFormatter.ofPattern("EE")
    return dateFormat.format(currDateTime)
}