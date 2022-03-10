package ru.dromanryuk.task_scheduler_android.featureTask.presentation.di

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.ScheduleUseCases
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.ScheduleViewModel

val taskSchedulerPresentationModule = module {
    single { ScheduleUseCases(get()) }
    viewModel { ScheduleViewModel(get()) }
}