package ru.dromanryuk.task_scheduler_android.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.ScheduleScreen
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.TaskScreen

object TaskScheduleScreen {
    object Schedule : Screen("ScheduleOverview")
    object Task: Screen("TaskDetails")
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.registerBathFeatureFlow(navController: NavController) {
    registerScreen(TaskScheduleScreen.Schedule) {
        ScheduleScreen()
    }
    registerScreen(TaskScheduleScreen.Task) {
        TaskScreen(navigateBack = { navController.navigate(TaskScheduleScreen.Schedule.route) })
    }
}
