package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoInputSection(
    modifier: Modifier = Modifier,
    firstName: String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    supportingText1: String,
    supportingText2: String,
) {
    Row(
        modifier = modifier
    ) {
        InputText(
            modifier = Modifier.weight(0.5f),
            value = firstName,
            onValueChange = { onFirstNameChange(it) },
            supportingText = supportingText1,
            shape = RoundedCornerShape(topStart = 25.dp)
        )
        InputText(
            modifier = Modifier.weight(0.5f),
            value = lastName,
            onValueChange = { onLastNameChange(it) },
            supportingText = supportingText2,
            shape = RoundedCornerShape(topEnd = 25.dp)
        )
    }
}