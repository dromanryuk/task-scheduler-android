package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dromanryuk.task_scheduler_android.featureTask.domain.model.Task
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleAction
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.ScheduleState
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.isCurrDay
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.model.toTaskViewStates
import java.time.LocalDateTime
import java.time.ZoneId

class ScheduleViewModel(private val useCases: ScheduleUseCases) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() = viewModelScope.launch {
        useCases.observeTasksUseCase()
            .collect { updateTasks(it) }
    }

    private suspend fun updateTasks(tasks: List<Task>) {
        val dayList = mutableListOf<Task>()
        tasks.forEach {
            if (LocalDateTime.ofInstant(it.time, ZoneId.systemDefault()).toLocalDate() == _state.value.selectedDate.toLocalDate())
                dayList.add(it)
        }
        _state.update {
            it.copy(tasksViewStates = dayList.toTaskViewStates())
        }
    }

    fun sendAction(action: ScheduleAction) {
        when (action) {
            is ScheduleAction.UpdateDateTimeDialogVisibility -> updateDateTimeDialogVisibility(action.newVal)
            is ScheduleAction.UpdateDate -> updateDate(action.dateTime)
            ScheduleAction.CreateTask -> createTask()
            ScheduleAction.ChangeToCurrentDay -> changeToCurrentDay()
        }
    }

    private fun updateDateTimeDialogVisibility(newVal: Boolean) {
        _state.update {
            it.copy(dateDialogVisibility = newVal)
        }
    }

    private fun updateDate(dateTime: LocalDateTime) {
        _state.update {
            it.copy(selectedDate = dateTime, isCurrDay = isCurrDay(dateTime))
        }
        observeTasks()
    }

    private fun createTask() = viewModelScope.launch {
        useCases.createTaskUseCase(_state.value.selectedDate) { id ->
            _state.update {
                it.copy(createdTaskId = id)
            }
        }
        observeTasks()
    }

    private fun changeToCurrentDay() {
        _state.update {
            it.copy(selectedDate = LocalDateTime.now(), isCurrDay = true)
        }
        observeTasks()
    }
}