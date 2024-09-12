package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubmitButton(
    onSubmitClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSubmitClick,
        border = BorderStroke(1.dp, color = ButtonDefaults.outlinedButtonColors().contentColor),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = ButtonDefaults.outlinedButtonColors().contentColor
        )
    ) {
        TopTitleSignUp(text = "Submit", size = 20.sp)
    }
}