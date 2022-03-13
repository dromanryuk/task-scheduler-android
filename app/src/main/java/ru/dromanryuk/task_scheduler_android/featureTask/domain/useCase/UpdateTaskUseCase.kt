package ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase

import kotlinx.coroutines.flow.collect
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository
import java.time.LocalDateTime
import java.time.ZoneId

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(params: Params) = updateTask(params)

    private suspend fun updateTask(params: Params) {
        val oldTask = taskRepository.observeById(params.id)
        oldTask.collect {
            val newTask = it.createUpdated(params)
            taskRepository.updateTask(newTask)
        }
    }

    private fun Task.createUpdated(params: Params) = copy(
        title = params.title,
        description = params.description,
        time = params.dateTime.atZone(ZoneId.systemDefault()).toInstant()
    )

    data class Params(
        val id: String,
        val title: String,
        val description: String,
        val dateTime: LocalDateTime
    )
}