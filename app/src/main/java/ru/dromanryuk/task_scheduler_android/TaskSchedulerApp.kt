package ru.dromanryuk.task_scheduler_android

import android.app.Application
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.dromanryuk.task_scheduler_android.featureTask.data.di.taskSchedulerModule
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.di.taskSchedulerPresentationModule

class TaskSchedulerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin{
            androidContext(this@TaskSchedulerApp)
            modules(taskSchedulerModule, taskSchedulerPresentationModule)
        }
    }
}