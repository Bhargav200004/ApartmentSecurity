package com.example.apartmentsecurity.ui.authentication.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.apartmentsecurity.ui.theme.onTertiaryContainerDark
import com.example.apartmentsecurity.ui.theme.onTertiaryContainerLight

@Composable
fun TextClickable(
    supportingText: String,
    clickableText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        val darkThemeOrNot = isSystemInDarkTheme()
        Text(text = supportingText)
        Text(
            modifier = Modifier
                .drawBehind {
                    drawLine(
                        color = if (!darkThemeOrNot) onTertiaryContainerLight else onTertiaryContainerDark,
                        start = Offset(x = 0f, y = size.height * 0.8f),
                        end = Offset(x = size.width, y = size.height * 0.8f),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {

                },
            text = clickableText,

            )
    }

}