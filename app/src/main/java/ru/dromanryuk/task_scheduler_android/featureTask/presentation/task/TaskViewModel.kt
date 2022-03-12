package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dromanryuk.task_scheduler_android.featureTask.domain.useCase.UpdateTaskUseCase
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.DropdownType
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.TaskEditingAction
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.TaskState
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.changeDialogVisibility
import java.time.LocalDateTime
import java.time.ZoneId

class TaskViewModel(
    private val useCases: TaskUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
//    private val taskId = savedStateHandle.get<String>("taskId")
//        ?: error("noteId argument is not passed")

    private val taskId = "2bc6f468-9ceb-4ed8-ba27-9e6ed7f548e3"

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    init {
        observeTask()
    }

    private fun observeTask() = viewModelScope.launch {
        useCases.observeTaskUseCase(taskId)
            .collect { task ->
                _state.update { state ->
                    state.copy(
                        titleState = task.title,
                        descriptionState = task.description,
                        dateTimePikersState = state.dateTimePikersState.copy(
                            currDateTime = LocalDateTime.ofInstant(task.time, ZoneId.systemDefault()))
                    )
                }
            }
    }

    fun sendAction(action: TaskEditingAction) {
        when (action) {
            is TaskEditingAction.OnTitleChanged -> changeTitle(action.title)
            is TaskEditingAction.OnDescriptionChanged -> changeDescription(action.description)
            is TaskEditingAction.UpdateDateTime -> updateDateTime(action.dateTime)
            is TaskEditingAction.UpdateDateTimeDialogVisibility ->
                updateDateTimeDialogVisibility(action.type, action.newVal)
            TaskEditingAction.ExitScreen -> {
                if (checkTaskFilling(_state.value)) {
                    removeTask()
                } else {
                    sendSaveEditingEvent()
                    onExitScreen()
                }
            }
            TaskEditingAction.SaveEditing -> onSaveEditing(_state.value)
        }
    }

    private fun changeTitle(title: String) = viewModelScope.launch {
        _state.update {
            it.copy(titleState = title)
        }
    }

    private fun changeDescription(description: String) = viewModelScope.launch {
        _state.update {
            it.copy(descriptionState = description)
        }
    }

    private fun updateDateTime(dateTime: LocalDateTime) = viewModelScope.launch {
        if (dateTime >= LocalDateTime.now()) {
            _state.update {
                it.copy(dateTimePikersState = it.dateTimePikersState.copy(currDateTime = dateTime,
                    timeDialogVisibility = false,
                    dateDialogVisibility = false))
            }
        }
    }

    private fun updateDateTimeDialogVisibility(type: DropdownType, newVal: Boolean) {
        _state.update {
            it.copy(dateTimePikersState = it.dateTimePikersState.changeDialogVisibility(type, newVal))
        }
    }

    private fun checkTaskFilling(state: TaskState): Boolean {
        return state.titleState.isEmpty() && state.descriptionState.isEmpty()
    }

    private fun removeTask() = viewModelScope.launch {
        onExitScreen()
        useCases.removeTaskUseCase(taskId)
    }

    private fun sendSaveEditingEvent() {
        sendAction(TaskEditingAction.SaveEditing)
    }

    private fun onExitScreen() {
        _state.update { it.copy(isExitFromScreen = true) }
    }

    private fun onSaveEditing(state: TaskState) = with(state) {
        viewModelScope.launch {
            useCases.updateTaskUseCase(
                UpdateTaskUseCase.Params(
                    id = taskId,
                    title = titleState,
                    description = descriptionState,
                    dateTime = dateTimePikersState.currDateTime
                )
            )
        }
    }
}