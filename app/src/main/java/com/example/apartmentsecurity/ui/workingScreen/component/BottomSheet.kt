package com.example.apartmentsecurity.ui.workingScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen.SecurityGuardScreenData
import com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen.SecurityGuardScreenEvent

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheet(
    uiState: SecurityGuardScreenData,
    onEvent: (SecurityGuardScreenEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onEvent(SecurityGuardScreenEvent.OnBottomSheetDismissClick) },
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.reasonList) { reason ->
                BottomButton(
                    reason = reason,
                    onReasonChange = {
                        onEvent(SecurityGuardScreenEvent.OnReasonChange(reason))
                        onEvent(SecurityGuardScreenEvent.OnBottomSheetDismissClick)
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomButton(
    reason: String,
    onReasonChange: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onReasonChange()
        }
    ) {
        Text(text = reason)
    }
}