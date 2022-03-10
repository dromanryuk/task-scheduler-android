package ru.dromanryuk.task_scheduler_android.featureTask.presentation.components

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDateTime
import java.time.ZoneOffset

fun showDatePicker(
    activity: AppCompatActivity,
    updateDate: (LocalDateTime) -> Unit,
    onCancel: () -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        val dateTime = LocalDateTime.ofEpochSecond(it/1000, 0, ZoneOffset.UTC)
        updateDate(LocalDateTime.of(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth, 0, 0))
    }
    picker.addOnCancelListener { onCancel() }
    picker.addOnDismissListener { onCancel() }
}

fun showTimePicker(
    activity: AppCompatActivity,
    currDate: LocalDateTime,
    updateTime: (LocalDateTime) -> Unit,
    onCancel: () -> Unit
) {
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updateTime(LocalDateTime.of(currDate.year, currDate.monthValue, currDate.dayOfMonth, picker.hour, picker.minute))
    }
    picker.addOnCancelListener { onCancel() }
    picker.addOnDismissListener { onCancel() }
}