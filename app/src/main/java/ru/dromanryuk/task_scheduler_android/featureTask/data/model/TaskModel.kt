package ru.dromanryuk.task_scheduler_android.featureTask.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import java.time.Instant
import java.util.*

open class TaskModel(
    @PrimaryKey
    var id: UUID = UUID.randomUUID(),
    var title: String = "",
    var description: String = "",
    var date: Date = Date()
) : RealmObject()

fun TaskModel.toTask() = Task(
    id = id.toString(),
    title = title,
    description = description,
    time = Instant.ofEpochMilli(date.time)
)

fun Task.toTaskModel() = TaskModel(
    title = title,
    description = description,
    date = Date(time.toEpochMilli())
)