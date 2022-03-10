package ru.dromanryuk.task_scheduler_android.featureTask.domain.model

import java.time.Instant

data class Task(
    val id: String,
    val title: String,
    val description: String,
    var time: Instant = Instant.now(),
)
