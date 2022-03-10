package ru.dromanryuk.task_scheduler_android.featureTask.data.operations

import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import io.realm.kotlin.executeTransactionAwait
import ru.dromanryuk.task_scheduler_android.featureTask.data.model.TaskModel
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import java.util.*

class DatabaseOperations(private val config: RealmConfiguration) {

    suspend fun insertCase(task: TaskModel): String {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(task)
        }
        return task.id.toString()
    }

    suspend fun observeTasks(): List<TaskModel> {
        val realm = Realm.getInstance(config)
        val tasks = mutableListOf<TaskModel>()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            tasks.addAll(realmTransaction
                .where(TaskModel::class.java)
                .findAll()
            )
        }
        return tasks
    }

    suspend fun observeTaskById(id: String): TaskModel? {
        val realm = Realm.getInstance(config)
        var task: TaskModel? = null
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            task = realmTransaction.where(TaskModel::class.java).equalTo("id", UUID.fromString(id)).findFirst()
        }
        return task
    }

    suspend fun updateTask(task: Task) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val updateTask = realmTransaction
                .where(TaskModel::class.java)
                .equalTo("id", UUID.fromString(task.id))
                .findFirst()
            if (updateTask != null) {
                updateTask.title = task.title
                updateTask.description = task.description
                updateTask.date = Date.from(task.time)
            }
        }
    }

    suspend fun removeTask(id: String) {
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction
                .where(TaskModel::class.java)
                .equalTo("id", UUID.fromString(id))
                .findFirst()
                ?.deleteFromRealm()
        }
    }
}