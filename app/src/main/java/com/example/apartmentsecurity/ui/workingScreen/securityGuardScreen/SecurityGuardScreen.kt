package com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen

import android.Manifest
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person3
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apartmentsecurity.R
import com.example.apartmentsecurity.ui.navigation.AppScreen
import com.example.apartmentsecurity.ui.workingScreen.component.BottomSheet
import com.example.apartmentsecurity.ui.workingScreen.component.DialogWithMessage
import com.example.apartmentsecurity.ui.workingScreen.component.SecurityGuardScreenTextField
import com.example.apartmentsecurity.util.SnackBarController
import com.example.apartmentsecurity.util.SnackBarEvent
import com.example.apartmentsecurity.util.getBitmapFromVectorDrawable
import kotlinx.coroutines.launch


@Composable
fun SecurityGuardScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SecurityGuardScreenTopBar(navController = navController)
        }
    ) { paddingValues ->


        val viewModel: SecurityGuardScreenViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()

        val scope = rememberCoroutineScope()




        val cameraLaunch = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview(),
            onResult = {
                viewModel.onEvent(SecurityGuardScreenEvent.OnPictureChange(it))
                Log.d("TAG", "CameraLearning: $it")
            }
        )

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGrant ->
                if (isGrant) {
                    cameraLaunch.launch(null)
                } else {
                    scope.launch {
                        SnackBarController.sendEvent(SnackBarEvent(message = "Camera permission Denied \n Please allow at setting"))
                    }
                }
            }
        )

        val scroll = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = scroll),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.showModalBottomSheet)
                BottomSheet(uiState = uiState, onEvent = viewModel::onEvent)

            PhotoSection(uiState, launcher)

            SecurityGuardForm(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun PhotoSection(
    uiState: SecurityGuardScreenData,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    Image(
        bitmap = if (uiState.pictureBitmap != null) {
            uiState.pictureBitmap.asImageBitmap()
        } else {
            getBitmapFromVectorDrawable(
                LocalContext.current, R.drawable.outline_person_24
            ).asImageBitmap()
        },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clickable {
                launcher.launch(Manifest.permission.CAMERA)
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
    if (uiState.showDialog) {
        DialogWithMessage(
            onDismissRequest = { onEvent(SecurityGuardScreenEvent.OnDialogDismissClick) },
            onConfirmation = {
                onEvent(SecurityGuardScreenEvent.OnDialogConfirmClick)
            },
            value = uiState.other,
            onValueChange = {
                onEvent(SecurityGuardScreenEvent.OnOtherChange(it))
            }
        )
    }
    OutlinedButton(
        onClick = {onEvent(SecurityGuardScreenEvent.OnSubmit)},
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = "Submit")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityGuardScreenTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Visitor Information")
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(route = AppScreen.MainScreen)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}
