package com.example.apartmentsecurity.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController , startDestination = AppScreen.MainScreen){

        composable<AppScreen.MainScreen> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(onClick = {navHostController.navigate(AuthScreen.AdminAuth)}) {
                    Text("Going for the Auth Screen")
                }
                Button(onClick = {navHostController.navigate(AuthScreen.UserAuth)}) {
                    Text("Going for the Auth Screen")
                }
                Button(onClick = {navHostController.navigate(AuthScreen.SecurityAuth)}) {
                    Text("Going for the Auth Screen")
                }
            }
        }

        navigation<AuthScreen.AdminAuth>(startDestination = AdminAuthScreen.Signup){
            composable<AdminAuthScreen.Signup> {
                Text("Admin Signup AuthScreen")
            }
            composable<AdminAuthScreen.Signin> {
                Text("Admin Signin AuthScreen")
            }
        }

        navigation<AuthScreen.UserAuth>(startDestination = UserAuthScreen.Signup){
            composable<UserAuthScreen.Signup>{
                Text("User Signup Screen")
            }
            composable<UserAuthScreen.Signin> {
                Text("User Signin Screen")
            }
        }

        navigation<AuthScreen.SecurityAuth>(startDestination = SecurityAuthScreen.Signup){
            composable<SecurityAuthScreen.Signup>{
                Text("Security Signup Screen")
            }
            composable<SecurityAuthScreen.Signin> {
                Text("Security Signin Screen")
            }
        }

    }
}
