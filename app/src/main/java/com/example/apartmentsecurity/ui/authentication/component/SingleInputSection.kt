package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.InputText

@Composable
fun SingleInputSection(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    supportingText: String,
    shape: Shape = RectangleShape,
    isError: Boolean = false
) {
    InputText(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        supportingText = supportingText,
        shape = shape,
        isError = isError
    )
}