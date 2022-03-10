package ru.dromanryuk.task_scheduler_android.featureTask.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task

interface TaskRepository {

    fun observeAll(): Flow<List<Task>>

    fun observeById(id: String): Flow<Task>

    suspend fun getById(id: String): Task

    suspend fun addTask(task: Task): String

    suspend fun updateTask(task: Task)

    suspend fun removeTask(id: String)
}