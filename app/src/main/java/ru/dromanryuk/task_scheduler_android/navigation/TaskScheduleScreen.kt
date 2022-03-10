package ru.dromanryuk.task_scheduler_android.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.ScheduleScreen

object TaskScheduleScreen {
    object Schedule : Screen("ScheduleOverview")
    object Task: Screen("TaskDetails")
}

fun NavGraphBuilder.registerBathFeatureFlow(navController: NavController) {
    registerScreen(TaskScheduleScreen.Schedule) {
        ScheduleScreen()
    }
}
