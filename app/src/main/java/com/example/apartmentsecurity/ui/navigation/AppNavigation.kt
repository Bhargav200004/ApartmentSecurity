package com.example.apartmentsecurity.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.apartmentsecurity.MainScreen
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin.AdminSignin
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.AdminSignup
import com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin.SecuritySignin
import com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignup.SecuritySignup
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin.UserSignin
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignup.UserSignup
import com.example.apartmentsecurity.ui.workingScreen.adminScreen.AdminScreen
import com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen.SecurityGuardScreen
import com.example.apartmentsecurity.ui.workingScreen.userScreen.UserScreen


@Composable
fun AppNavigation() {

    val navHostController = rememberNavController()


    NavHost(navController = navHostController , startDestination = AppScreen.MainScreen){

        composable<AppScreen.MainScreen> {
            MainScreen(navController = navHostController)
        }

        navigation<AuthScreen.AdminAuth>(startDestination = AdminAuthScreen.Signup){
            composable<AdminAuthScreen.Signup> {
                AdminSignup(navController = navHostController )
            }
            composable<AdminAuthScreen.Signin> {
                AdminSignin(navController = navHostController)
            }
            composable<AdminScreen.Admin> {
                AdminScreen(navController = navHostController)
            }
        }

        navigation<AuthScreen.UserAuth>(startDestination = UserAuthScreen.Signup){
            composable<UserAuthScreen.Signup>{
                UserSignup(navController =  navHostController)
            }
            composable<UserAuthScreen.Signin> {
                UserSignin(navController = navHostController)
            }
            composable<UserScreen.User> {
                UserScreen(navController = navHostController)
            }
        }

        navigation<AuthScreen.SecurityAuth>(startDestination = SecurityAuthScreen.Signup){
            composable<SecurityAuthScreen.Signup>{
                SecuritySignup(navController = navHostController)
            }
            composable<SecurityAuthScreen.Signin> {
                SecuritySignin(navController =  navHostController)
            }
            composable<SecurityScreen.Security> {
                SecurityGuardScreen(navController =  navHostController)
            }
        }

    }
}
