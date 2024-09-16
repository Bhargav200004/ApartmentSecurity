package com.example.apartmentsecurity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin.UserSignin
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignup.UserSignup
import com.example.apartmentsecurity.ui.theme.ApartmentSecurityTheme
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
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        UserSignin()
                    }
                }
            }
        }
    }
}