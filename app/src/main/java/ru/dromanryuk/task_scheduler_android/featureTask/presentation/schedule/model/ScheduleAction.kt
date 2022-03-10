package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model

import java.time.LocalDateTime

sealed class ScheduleAction {
    object CreateTask : ScheduleAction()

    object ChangeToCurrentDay : ScheduleAction()

    data class UpdateDateTimeDialogVisibility(val newVal: Boolean) : ScheduleAction()

    data class UpdateDate(val dateTime: LocalDateTime) : ScheduleAction()
}