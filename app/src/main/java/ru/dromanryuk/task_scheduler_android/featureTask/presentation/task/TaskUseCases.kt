package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task

import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.ObserveTaskUseCase
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.RemoveTaskUseCase
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.UpdateTaskUseCase

class TaskUseCases(taskRepository: TaskRepository) {
    val observeTaskUseCase = ObserveTaskUseCase(taskRepository)
    val updateTaskUseCase = UpdateTaskUseCase(taskRepository)
    val removeTaskUseCase = RemoveTaskUseCase(taskRepository)
}