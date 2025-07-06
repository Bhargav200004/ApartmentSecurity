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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apartmentsecurity.ui.authentication.component.AppTopBar
import com.example.apartmentsecurity.ui.authentication.component.PasswordSection
import com.example.apartmentsecurity.ui.authentication.component.SingleInputSection
import com.example.apartmentsecurity.ui.authentication.component.SubmitButton
import com.example.apartmentsecurity.ui.authentication.component.TextClickable
import com.example.apartmentsecurity.ui.authentication.component.TopTitleSignUp
import com.example.apartmentsecurity.ui.navigation.AdminAuthScreen
import com.example.apartmentsecurity.ui.navigation.AdminScreen
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


        LaunchedEffect(key1 = uiState.navigationApproval) {
            if (uiState.navigationApproval) navController.navigate(route = AdminScreen.Admin)
        }

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
                    viewModel.onEvent(AdminSigninEvent.OnSubmitButtonClick)
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
            value = uiState.apartmentId,
            onValueChange = { onEvent(AdminSigninEvent.OnApartmentIdChange(it)) },
            supportingText = "Apartment Id",
            shape = RectangleShape,
            isError = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.apartmentName,
            onValueChange = { onEvent(AdminSigninEvent.OnApartmentNameChange(it)) },
            supportingText = "Apartment Name",
            shape = RectangleShape,
            isError = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.userName,
            onValueChange = { onEvent(AdminSigninEvent.OnUserNameChange(it)) },
            supportingText = "User Name",
            shape = RectangleShape,
            isError = false
        )
        Spacer(modifier = Modifier.height(20.dp))
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.email,
            onValueChange = { onEvent(AdminSigninEvent.OnEmailChange(it)) },
            supportingText = "Email",
            shape = RectangleShape,
            isError = false
        )
        Spacer(modifier = Modifier.height(20.dp))
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
