package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit

@Composable
fun TopTitleSignUp(
    text: String, size: TextUnit
) {
    Text(
        text = text, style = MaterialTheme.typography.displayMedium.copy(fontSize = size)
    )
}