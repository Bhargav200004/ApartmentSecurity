package com.example.apartmentsecurity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin.UserSignin
import com.example.apartmentsecurity.ui.navigation.AppNavigation
import com.example.apartmentsecurity.ui.theme.ApartmentSecurityTheme
import com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen.SecurityGuardScreen
import com.example.apartmentsecurity.ui.workingScreen.userScreen.UserScreen
import com.example.apartmentsecurity.util.ObserverAsEvents
import com.example.apartmentsecurity.util.SnackBarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApartmentSecurityTheme {

                val snackBarHostState = remember{
                    SnackbarHostState()
                }

                val scope = rememberCoroutineScope()

                ObserverAsEvents(flow = SnackBarController.event) {event->
                    scope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()

                        val result = snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel?.label,
                            duration = SnackbarDuration.Short
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            event.actionLabel?.action?.invoke()
                        }
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    },
                    modifier = Modifier.fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}