package ru.dromanryuk.task_scheduler_android.featureTask.presentation.schedule.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardActions: KeyboardActions = KeyboardActions {  },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.body1,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.body1
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            cursorColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.surface,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}