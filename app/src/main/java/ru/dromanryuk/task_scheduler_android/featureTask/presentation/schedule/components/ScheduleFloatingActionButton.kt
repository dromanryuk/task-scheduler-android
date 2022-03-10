package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleFloatingActionButton(
    onAddClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier
            .padding(5.dp),
        onClick = {
            onAddClick()
        },
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(25)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "",
            tint = MaterialTheme.colors.secondary
        )
    }
}

