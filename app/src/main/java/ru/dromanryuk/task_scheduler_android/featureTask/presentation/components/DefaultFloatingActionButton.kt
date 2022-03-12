package ru.dromanryuk.task_scheduler_android.featureTask.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DefaultFloatingActionButton(
    onClick: () -> Unit,
    icon: ImageVector,
) {
    FloatingActionButton(
        modifier = Modifier
            .padding(5.dp),
        onClick = { onClick() },
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(25)
    ) {
        Icon(
            icon,
            contentDescription = "",
            tint = MaterialTheme.colors.secondary
        )
    }
}