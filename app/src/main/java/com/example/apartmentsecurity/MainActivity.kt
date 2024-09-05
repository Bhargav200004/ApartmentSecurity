package com.example.apartmentsecurity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.AdminSignup
import com.example.apartmentsecurity.ui.theme.ApartmentSecurityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApartmentSecurityTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column(
//                        modifier = Modifier
//
//                            .fillMaxSize()
//                    ) {
                        AdminSignup()
//                    }
//                }
            }
        }
    }
}