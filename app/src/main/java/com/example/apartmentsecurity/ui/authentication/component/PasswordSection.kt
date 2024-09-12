package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordSection(
    modifier : Modifier = Modifier,
    value: String,
    isVisible: Boolean,
    onValueChange: (String) -> Unit,
    onEyeButtonClick: (Boolean) -> Unit,
    supportingText: String,
    shape: Shape = RectangleShape,
    next: ImeAction,
    isError : Boolean
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        shape = shape ,
        placeholder = { Text(text = supportingText, style = MaterialTheme.typography.titleMedium) },
        maxLines = 1,
        visualTransformation = if (!isVisible) PasswordVisualTransformation(mask = '*') else VisualTransformation.None,
        suffix = {
            IconButton(
                modifier = Modifier
                    .size(20.dp),
                onClick = { onEyeButtonClick(isVisible) }
            ) {
                Icon(
                    imageVector = if (isVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = next
        ),
        isError = isError
    )
}