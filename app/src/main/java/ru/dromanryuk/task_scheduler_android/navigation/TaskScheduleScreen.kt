package ru.dromanryuk.task_scheduler_android.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.ScheduleScreen
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.TaskScreen

object TaskScheduleScreen {
    object Schedule : Screen("ScheduleOverview")
    object Task: Screen("TaskDetails")
}

@ExperimentalComposeUiApi
fun NavGraphBuilder.registerTaskFeatureFlow(navController: NavController) {
    var taskId = ""
    registerScreen(TaskScheduleScreen.Schedule) {
        ScheduleScreen(
            navigateToTaskScreen = {
                taskId = it
                navController.navigate(TaskScheduleScreen.Task.route)
            }
        )
    }
    registerScreen(TaskScheduleScreen.Task) {
        TaskScreen(
            navigateBack = {
                navController.popBackStack()
            },
            taskId = taskId
        )
    }
}
