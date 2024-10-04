package com.example.apartmentsecurity.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.apartmentsecurity.AuthenticationType
import com.example.apartmentsecurity.MainScreen
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin.AdminSignin
import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.AdminSignup
import com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin.SecuritySignin
import com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignup.SecuritySignup
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin.UserSignin
import com.example.apartmentsecurity.ui.authentication.userauthentication.usersignup.UserSignup


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navHostController = rememberNavController()

    val viewModel: AppNavigationViewModel = hiltViewModel()

    val authenticationType by viewModel.state.collectAsState()


    NavHost(navController = navHostController , startDestination = AppScreen.MainScreen){

        composable<AppScreen.MainScreen> {
            MainScreen(navController = navHostController)
        }

        navigation<AuthScreen.AdminAuth>(startDestination = if( (authenticationType != AuthenticationType.ADMIN.name) && (authenticationType == AuthenticationType.UNAUTHENTICATED.name) ) AdminAuthScreen.Signup else AdminScreen.Admin){
            composable<AdminAuthScreen.Signup> {
                AdminSignup(navController = navHostController )
            }
            composable<AdminAuthScreen.Signin> {
                AdminSignin(navController = navHostController)
            }
            composable<AdminScreen.Admin> {
                Text("Admin Screen")
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
                Text("User Screen")
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
                Text("Security Screen")
            }
        }

    }
}
