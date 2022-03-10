package ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase

import kotlinx.coroutines.flow.Flow
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository

class ObserveTasksUseCase(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<List<Task>> = taskRepository.observeAll()
}