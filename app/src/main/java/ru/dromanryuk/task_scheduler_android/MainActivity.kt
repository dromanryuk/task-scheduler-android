package ru.dromanryuk.task_scheduler_android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.dromanryuk.task_scheduler_android.navigation.AppNavigationFlow
import ru.dromanryuk.task_scheduler_android.ui.theme.SchedulerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchedulerTheme {
                AppNavigationFlow()
            }
        }
    }
}
