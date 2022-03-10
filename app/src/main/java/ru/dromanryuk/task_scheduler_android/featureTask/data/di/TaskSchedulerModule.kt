package ru.dromanryuk.task_scheduler_android.featureTask.data.di

import io.realm.RealmConfiguration
import org.koin.dsl.module
import ru.dromanryuk.task_scheduler_android.featureTask.data.operations.DatabaseOperations
import ru.dromanryuk.task_scheduler_android.featureTask.data.repository.TaskRepositoryImpl
import ru.dromanryuk.task_scheduler_android.featureTask.domain.repository.TaskRepository

val taskSchedulerModule = module {
    single<RealmConfiguration> {
        RealmConfiguration.Builder()
        .schemaVersion(1L)
        .build()
    }
    single { DatabaseOperations(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}
