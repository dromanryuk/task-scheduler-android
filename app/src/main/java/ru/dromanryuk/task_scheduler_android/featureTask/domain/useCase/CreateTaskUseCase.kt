package ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository
import java.time.LocalDateTime
import java.time.ZoneId

class CreateTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(date: LocalDateTime, onTaskClick: suspend (id: String) -> Unit) =
        withContext(Dispatchers.IO) {
            createTaskModel(
                Task(
                    id = "",
                    title = "",
                    description = "",
                    time = date.atZone(ZoneId.systemDefault()).toInstant(),
                ),
                onTaskClick
            )
        }

    private suspend fun createTaskModel(task: Task, onTaskClick: suspend (id: String) -> Unit) {
        withContext(Dispatchers.IO) {
            onTaskClick(taskRepository.addTask(task))
        }
    }
}