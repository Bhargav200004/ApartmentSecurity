package com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apartmentsecurity.R

@Composable
fun SecurityGuardScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SecurityGuardScreenTopBar()
        }
    ) { paddingValues ->


        val viewModel: SecurityGuardScreenViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()


        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.showModalBottomSheet)
                BottomSheet(uiState = uiState, onEvent = viewModel::onEvent)

            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {

                    }
                    .size(200.dp)
                    .border(
                        BorderStroke(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
            )

            SecurityGuardForm(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )

            DialogWithImage(
                onDismissRequest = {},
                onConfirmation = {},
                value = "asdfa",
                onValueChange = {}
            )


        }
    }
}


@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "OTHER",
                    modifier = Modifier.padding(16.dp),
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    shape = RoundedCornerShape(12.dp),
                    label = { Text(text = "Reason") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


@Composable
private fun SecurityGuardForm(
    uiState: SecurityGuardScreenData,
    onEvent: (SecurityGuardScreenEvent) -> Unit
) {
    SecurityGuardScreenTextField(
        value = uiState.name,
        onValueChange = { onEvent(SecurityGuardScreenEvent.OnNameChange(it)) },
        leadingIcon = Icons.Outlined.Person3,
        placeholder = "Name"
    )
    SecurityGuardScreenTextField(
        value = uiState.phoneNumber,
        onValueChange = { onEvent(SecurityGuardScreenEvent.OnPhoneNumberChange(it)) },
        leadingIcon = Icons.Outlined.Call,
        placeholder = "Phone Number",
        keyboardType = KeyboardType.Number
    )
    SecurityGuardScreenTextField(
        value = uiState.vehicleNumber,
        onValueChange = { onEvent(SecurityGuardScreenEvent.OnVehicleNumberChange(it)) },
        leadingIcon = Icons.Outlined.Map,
        placeholder = "Vehicle Number"
    )
    SecurityGuardScreenTextField(
        value = uiState.roomNumber,
        onValueChange = { onEvent(SecurityGuardScreenEvent.OnRoomNumberChange(it)) },
        leadingIcon = Icons.Outlined.Room,
        placeholder = "Room Number",
        imeAction = ImeAction.Done
    )
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        onClick = {
            onEvent(SecurityGuardScreenEvent.OnBottomSheetInputClick)
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = uiState.reason)
    }


}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheet(
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
    reason : String,
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


@Composable
fun SecurityGuardScreenTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        placeholder = { Text(text = placeholder) },
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityGuardScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Visitor Information")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}
