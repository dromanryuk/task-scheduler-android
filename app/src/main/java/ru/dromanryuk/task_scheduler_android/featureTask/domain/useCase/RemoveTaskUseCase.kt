package ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase

import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository

class RemoveTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String) {
        taskRepository.removeTask(id)
    }
}