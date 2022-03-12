package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule

import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.CreateTaskUseCase
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.ObserveTasksUseCase

class ScheduleUseCases(taskRepository: TaskRepository) {
    val observeTasksUseCase = ObserveTasksUseCase(taskRepository)
    val createTaskUseCase = CreateTaskUseCase(taskRepository)
}