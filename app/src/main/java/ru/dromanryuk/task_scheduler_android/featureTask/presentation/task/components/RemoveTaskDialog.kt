package ru.dromanryuk.task_scheduler_android.featureTask.presentation.task.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultDialog
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultDialogButtons
import ru.dromanryuk.task_scheduler_android.featureTask.presentation.components.DefaultDialogTitle

@Composable
fun RemoveTaskDialog(
    onDismiss: () -> Unit,
    onRemoveTask: () -> Unit,
) {
    DefaultDialog(
        onDismissRequest = onDismiss,
        shapeRadius = 5
    ) {
        RemoveTaskDialogContent(
            onDismiss = onDismiss,
            onRemoveTask
        )
    }
}

@Composable
fun RemoveTaskDialogContent(
    onDismiss: () -> Unit,
    onRemoveNotePassword: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        DefaultDialogTitle("Вы хотите удалить задачу?", 16.sp)
        DefaultDialogButtons(
            arrangement = Arrangement.Center,
            confirmButtonText = "Удалить",
            dismissButtonText = "Отмена",
            onConfirmClick = {
                onRemoveNotePassword()
                onDismiss()
            },
            onDismissClick = { onDismiss() }
        )
    }
}