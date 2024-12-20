package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.apartmentsecurity.ui.authentication.component.TwoInputSection
import com.example.apartmentsecurity.ui.navigation.AdminAuthScreen
import kotlin.reflect.KFunction1

@Composable
fun AdminSignup(navController: NavController) {
    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        val viewModel: AdminSignupViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()

        viewModel.onErrorChange()

        LaunchedEffect(key1 = uiState.navigationApproval) {
            if(uiState.navigationApproval) navController.navigate(route = AdminAuthScreen.Signin)
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            if (uiState.circularProgressionBarShow) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                TopTitleSignUp(
                    text = "ADMIN SIGNUP", size = 40.sp
                )

                SignupFormSection(
                    state = uiState,
                    onEvent = viewModel::onEvent,
                )

                SubmitButton(
                    onSubmitClick = {
                        viewModel.onEvent(AdminSignupEvent.OnSubmitClick)

                    }
                )
                TextClickable(
                    supportingText = "Already have Account? ",
                    clickableText = "Log In",
                    onTextClick = {
                        navController.navigate(route = AdminAuthScreen.Signin)
                    }
                )
            }
        }
    }
}




@Composable
private fun SignupFormSection(
    state: AdminSignupData,
    onEvent: KFunction1<AdminSignupEvent, Unit>
) {
    Column {
        TwoInputSection(
            modifier = Modifier.fillMaxWidth(),
            firstName = state.firstName,
            lastName = state.lastName,
            onFirstNameChange = { onEvent(AdminSignupEvent.OnFirstNameChange(it)) },
            onLastNameChange = { onEvent(AdminSignupEvent.OnLastNameChange(it)) },
            supportingText1 = "FIRST NAME",
            supportingText2 = "LAST NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = { onEvent(AdminSignupEvent.OnEmailChange(it)) },
            supportingText = "EMAIL ADDRESS",
            isError = state.emailError

        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.apartmentName,
            onValueChange = { onEvent(AdminSignupEvent.OnApartmentNameChange(it)) },
            supportingText = "APARTMENT NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.apartmentId,
            onValueChange = { onEvent(AdminSignupEvent.OnApartmentIdChange(it)) },
            supportingText = "APARTMENT UNIQUE ID"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.userName,
            onValueChange = { onEvent(AdminSignupEvent.OnUserNameChange(it)) },
            supportingText = "USERNAME"
        )
        PasswordSection(
            value = state.password,
            isVisible = state.isPasswordVisible,
            onValueChange = { onEvent(AdminSignupEvent.OnPasswordChange(it)) },
            onEyeButtonClick = { onEvent(AdminSignupEvent.OnPasswordVisibilityChange(it)) },
            supportingText = "PASSWORD",
            next = ImeAction.Next,
            isError = state.passwordError
        )
        PasswordSection(
            value = state.confirmPassword,
            isVisible = state.isConfirmPasswordVisible,
            onValueChange = { onEvent(AdminSignupEvent.OnConfirmPasswordChange(it)) },
            onEyeButtonClick = { onEvent(AdminSignupEvent.OnConfirmPasswordVisibilityChange(it)) },
            supportingText = "CONFIRM PASSWORD",
            shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
            next = ImeAction.Done,
            isError = state.passwordError
        )
    }

}




