package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apartmentsecurity.ui.authentication.component.AppTopBar
import com.example.apartmentsecurity.ui.authentication.component.InputText
import com.example.apartmentsecurity.ui.authentication.component.PasswordSection
import com.example.apartmentsecurity.ui.authentication.component.SingleInputSection
import com.example.apartmentsecurity.ui.authentication.component.SubmitButton
import com.example.apartmentsecurity.ui.authentication.component.TextClickable
import com.example.apartmentsecurity.ui.authentication.component.TopTitleSignUp
import com.example.apartmentsecurity.ui.authentication.component.TwoInputSection
import com.example.apartmentsecurity.ui.navigation.UserAuthScreen
import kotlin.reflect.KFunction1


@Composable
fun UserSignup(navController: NavController) {
    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        val viewModel: UserSignupViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = uiState.navigationApproval){
            if (uiState.navigationApproval) navController.navigate(route = UserAuthScreen.Signin)
        }

        viewModel.onErrorChange()

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
                    text = "USER SIGNUP", size = 40.sp
                )

                SignupFormSection(
                    state = uiState,
                    onEvent = viewModel::onEvent,
                )

                SubmitButton(
                    onSubmitClick = {
                        viewModel.onEvent(UserSignupEvent.OnSubmitClick)
                    }
                )
                TextClickable(
                    supportingText = "Already have Account? ",
                    clickableText = "Log In",
                    onTextClick = {
                        navController.navigate(route = UserAuthScreen.Signin)
                    }
                )
            }
        }
    }
}

@Composable
private fun SignupFormSection(
    state: UserSignupData,
    onEvent: KFunction1<UserSignupEvent, Unit>
) {

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scroll)
    ) {
        TwoInputSection(
            modifier = Modifier.fillMaxWidth(),
            firstName = state.firstName,
            lastName = state.lastName,
            onFirstNameChange = { onEvent(UserSignupEvent.OnFirstNameChange(it)) },
            onLastNameChange = { onEvent(UserSignupEvent.OnLastNameChange(it)) },
            supportingText1 = "FIRST NAME",
            supportingText2 = "LAST NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = { onEvent(UserSignupEvent.OnEmailChange(it)) },
            supportingText = "EMAIL ADDRESS",
            isError = state.emailError

        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.apartmentName,
            onValueChange = { onEvent(UserSignupEvent.OnApartmentNameChange(it)) },
            supportingText = "APARTMENT NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.apartmentId,
            onValueChange = { onEvent(UserSignupEvent.OnApartmentIdChange(it)) },
            supportingText = "APARTMENT UNIQUE ID"
        )
        UserTwoInputSection(
            userName = state.userName,
            roomNo = state.roomNo,
            onUserNameChange = {onEvent(UserSignupEvent.OnUserNameChange(it))},
            onRoomNoChange = {onEvent(UserSignupEvent.OnUserRoomNoChange(it))}

        )
        PasswordSection(
            value = state.password,
            isVisible = state.isPasswordVisible,
            onValueChange = { onEvent(UserSignupEvent.OnPasswordChange(it)) },
            onEyeButtonClick = { onEvent(UserSignupEvent.OnPasswordVisibilityChange(it)) },
            supportingText = "PASSWORD",
            next = ImeAction.Next,
            isError = state.passwordError
        )
        PasswordSection(
            value = state.confirmPassword,
            isVisible = state.isConfirmPasswordVisible,
            onValueChange = { onEvent(UserSignupEvent.OnConfirmPasswordChange(it)) },
            onEyeButtonClick = { onEvent(UserSignupEvent.OnConfirmPasswordVisibilityChange(it)) },
            supportingText = "CONFIRM PASSWORD",
            shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
            next = ImeAction.Done,
            isError = state.passwordError
        )
    }
}

@Composable
private fun UserTwoInputSection(
    userName : String,
    roomNo : String,
    onUserNameChange : (String) -> Unit,
    onRoomNoChange : (String) -> Unit

) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        InputText(
            modifier = Modifier.weight(0.5f),
            value = userName ,
            onValueChange = { onUserNameChange(it)},
            supportingText = "UserName",
            shape = RectangleShape
        )
        InputText(
            modifier = Modifier.weight(0.5f),
            value = roomNo,
            onValueChange = { onRoomNoChange(it)},
            supportingText = "Room No",
            shape = RectangleShape,
            keyboardType = KeyboardType.Number
        )
    }
}

