package ru.dromanryuk.task_scheduler_android.featureTask.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.dromanryuk.task_scheduler_android.featureTask.data.model.toTask
import ru.dromanryuk.task_scheduler_android.featureTask.data.model.toTaskModel
import ru.dromanryuk.task_scheduler_android.featureTask.data.operations.DatabaseOperations
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val databaseOperations: DatabaseOperations
) : TaskRepository {
    override fun observeAll(): Flow<List<Task>> = flow {
        emit(databaseOperations.observeTasks().map {
            it.toTask()
        })
    }.flowOn(Dispatchers.IO)

    override fun observeById(id: String): Flow<Task> = flow {
        if (databaseOperations.observeTaskById(id) != null) {
            emit(databaseOperations.observeTaskById(id)!!.toTask())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getById(id: String): Task {
        return databaseOperations.observeTaskById(id)!!.toTask()
    }

    override suspend fun addTask(task: Task): String {
        return databaseOperations.insertCase(task.toTaskModel())
    }

    override suspend fun updateTask(task: Task) {
        databaseOperations.updateTask(task)
    }

    override suspend fun removeTask(id: String) {
        databaseOperations.removeTask(id)
    }
}