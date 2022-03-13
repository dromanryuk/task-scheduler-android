package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TaskViewState(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDateTime,
)

suspend fun List<Task>.toTaskViewStates() = withContext(Dispatchers.Default) {
    map { it.toTaskViewState() }
}

private fun Task.toTaskViewState() = TaskViewState(
    id = id,
    title = title,
    description = description,
    date = LocalDateTime.ofInstant(time, ZoneId.systemDefault())
)

private fun setTimeFormat(date: LocalDateTime): String {
    val timeFormat = DateTimeFormatter.ofPattern("dd MMMM. yyyy., HH:mm")
    return timeFormat.format(date)
}