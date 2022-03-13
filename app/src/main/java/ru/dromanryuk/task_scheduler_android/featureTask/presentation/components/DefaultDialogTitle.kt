package ru.dromanryuk.task_scheduler_android.featureTask.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun DefaultDialogTitle(text: String, fontSize: TextUnit) {
    Text(
        text = text,
        color = MaterialTheme.colors.surface,
        maxLines = 1,
        fontSize = fontSize,
        overflow = TextOverflow.Ellipsis
    )
}