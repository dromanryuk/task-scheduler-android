package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components.DefaultTextField
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.DropdownType
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.DropdownType.*
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.model.TaskState
import java.time.format.DateTimeFormatter

@ExperimentalComposeUiApi
@Composable
fun TaskScreenContent(
    task: TaskState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onDateTimeChange: (DropdownType) -> Unit,
) {
    val scrollState = rememberScrollState()
    val (first, second) = FocusRequester.createRefs()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(10.dp)
            .background(MaterialTheme.colors.background),
    ) {
        val focusManager = LocalFocusManager.current
        val focusRequester = FocusRequester()
        DefaultTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusOrder(first) { down = second },
            value = task.titleState,
            onValueChange = onTitleChange,
            placeholder = "Наименование",
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        DefaultTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusOrder(second),
            value = task.descriptionState,
            onValueChange = onContentChange,
            placeholder = "Описание"
        )
        DateTimeEdit(task, onDateTimeChange)
    }
}

@Composable
fun DateTimeEdit(
    task: TaskState,
    onDateTimeChange: (DropdownType) -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        DateTimeItem(
            text = task.dateTimePikersState.currDateTime.format(DateFormat),
            icon = Icons.Outlined.EditCalendar,
            onClick = { onDateTimeChange(DATE) }
        )
        DateTimeItem(
            text = task.dateTimePikersState.currDateTime.format(TimeFormat),
            icon = Icons.Outlined.AccessTime,
            onClick = { onDateTimeChange(TIME) }
        )
    }
}

@Composable
fun DateTimeItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick() },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon,
            contentDescription = "",
            tint = MaterialTheme.colors.secondary
        )
        Text(
            text = text,
            color = MaterialTheme.colors.surface,
            style = MaterialTheme.typography.body1
        )
    }
}

val DateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
val TimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

