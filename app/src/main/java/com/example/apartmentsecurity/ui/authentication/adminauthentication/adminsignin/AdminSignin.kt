package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.apartmentsecurity.ui.authentication.component.AppTopBar
import com.example.apartmentsecurity.ui.authentication.component.PasswordSection
import com.example.apartmentsecurity.ui.authentication.component.SingleInputSection
import com.example.apartmentsecurity.ui.authentication.component.SubmitButton
import com.example.apartmentsecurity.ui.authentication.component.TextClickable
import com.example.apartmentsecurity.ui.authentication.component.TopTitleSignUp
import com.example.apartmentsecurity.ui.navigation.AdminAuthScreen
import com.example.apartmentsecurity.ui.navigation.AppScreen
import kotlin.reflect.KFunction1

@Composable
fun AdminSignin(navController: NavController) {
    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = {
                    navController.popBackStack(route = AppScreen.MainScreen , inclusive = false)
                }
            )
        }
    ) { paddingValues ->

        val viewModel: AdminSigninViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {

            TopTitleSignUp(
                text = "ADMIN SIGNIN", size = 40.sp
            )

            AdminSignInForm(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )

            SubmitButton(
                onSubmitClick = {
                    navController.navigate(route = AppScreen.MainScreen)
//                    viewModel.onEvent(AdminSigninEvent.OnSubmitButtonClick)
                }
            )

            TextClickable(
                supportingText = "Don't have Account? ",
                clickableText = "SignUp",
                onTextClick = {
                    navController.navigate(route = AdminAuthScreen.Signup)
                }
            )

        }
    }

}

@Composable
private fun AdminSignInForm(
    uiState: AdminSigninData,
    onEvent: KFunction1<AdminSigninEvent, Unit>,
    ) {
    Column {
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.email,
            onValueChange = { onEvent(AdminSigninEvent.OnEmailChange(it)) },
            supportingText = "Email",
            shape = RectangleShape,
            isError = false
        )
        Spacer(modifier = Modifier.height(40.dp))
        PasswordSection(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password,
            isVisible = uiState.passwordVisible,
            onValueChange = {  onEvent(AdminSigninEvent.OnPasswordChange(it)) },
            onEyeButtonClick = { onEvent(AdminSigninEvent.OnPasswordVisibleChange(show = it))},
            supportingText = "Password",
            shape = RectangleShape,
            next = ImeAction.Done,
            isError = false
        )
    }
}
