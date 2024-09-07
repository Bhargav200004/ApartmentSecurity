package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apartmentsecurity.ui.theme.onTertiaryContainerDark
import com.example.apartmentsecurity.ui.theme.onTertiaryContainerLight
import kotlin.reflect.KFunction1

@Composable
fun AdminSignup() {
    Scaffold(
        topBar = {AdminSignUpTopBar(
            onBackClick = {}
        )}
    ) { paddingValues ->

        val viewModel: AdminSignupViewModel = hiltViewModel()

        val uiState by viewModel.state.collectAsStateWithLifecycle()

        viewModel.onErrorChange()
        LaunchedEffect(
            key1 = Unit
        ) {
            viewModel.onErrorChange()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            TopTitleAdminSignUp(
                text = "ADMIN SIGNUP", size = 40.sp
            )

            SignupFormSection(
                state = uiState,
                onEvent = viewModel::onEvent,
            )

            SubmitButton(
                onSubmitClick = viewModel::onEvent
            )

            TextClickable()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSignUpTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun SubmitButton(
    onSubmitClick: KFunction1<AdminSignupEvent, Unit>
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onSubmitClick(AdminSignupEvent.OnSubmitClick) },
        border = BorderStroke(1.dp, color = ButtonDefaults.outlinedButtonColors().contentColor),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = ButtonDefaults.outlinedButtonColors().contentColor
        )
    ) {
        TopTitleAdminSignUp(text = "Submit", size = 20.sp)
    }
}

@Composable
fun TextClickable() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        val darkThemeOrNot = isSystemInDarkTheme()
        Text(text = "Already have Account? ")
        Text(
            modifier = Modifier
                .drawBehind {
                    drawLine(
                        color = if (!darkThemeOrNot) onTertiaryContainerLight else onTertiaryContainerDark,
                        start = Offset(x = 0f, y = size.height * 0.8f),
                        end = Offset(x = size.width, y = size.height * 0.8f),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {

                },
            text = "Log In",

            )
    }

}

@Composable
fun SignupFormSection(
    state: AdminSignupData,
    onEvent: KFunction1<AdminSignupEvent, Unit>
) {
    Column {
        TwoInputSection(
            modifier = Modifier.fillMaxWidth(),
            firstName = state.firstName,
            lastName = state.lastName,
            onFirstNameChange = onEvent,
            onLastNameChange =onEvent,
            supportingText1 = "FIRST NAME",
            supportingText2 = "LAST NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = {onEvent(AdminSignupEvent.OnEmailChange(it))},
            supportingText = "EMAIL ADDRESS",
            isError = state.emailError

        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.apartmentName,
            onValueChange = {onEvent(AdminSignupEvent.OnApartmentNameChange(it))},
            supportingText = "APARTMENT NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            value = state.userName,
            onValueChange = {onEvent(AdminSignupEvent.OnUserNameChange(it))},
            supportingText = "USERNAME"
        )
        PasswordSection(
            value = state.password,
            isVisible = state.isPasswordVisible,
            onValueChange = { onEvent(AdminSignupEvent.OnPasswordChange(it)) },
            onEyeButtonClick = { onEvent(AdminSignupEvent.OnPasswordVisibilityChange(it))},
            supportingText = "PASSWORD",
            next = ImeAction.Next,
            isError = state.passwordError
        )
        PasswordSection(
            value = state.confirmPassword ,
            isVisible = state.isConfirmPasswordVisible,
            onValueChange = { onEvent(AdminSignupEvent.OnConfirmPasswordChange(it))},
            onEyeButtonClick = {onEvent(AdminSignupEvent.OnConfirmPasswordVisibilityChange(it))},
            supportingText = "CONFIRM PASSWORD",
            shape = RoundedCornerShape(bottomStart = 25.dp , bottomEnd = 25.dp),
            next = ImeAction.Done,
            isError = state.passwordError
        )
    }

}


@Composable
fun PasswordSection(
    modifier : Modifier = Modifier,
    value: String,
    isVisible: Boolean,
    onValueChange: (String) -> Unit,
    onEyeButtonClick: (Boolean) -> Unit,
    supportingText: String,
    shape: Shape = RectangleShape,
    next: ImeAction,
    isError : Boolean
    ) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        shape = shape ,
        placeholder = { Text(text = supportingText, style = MaterialTheme.typography.titleMedium) },
        maxLines = 1,
        visualTransformation = if (!isVisible) PasswordVisualTransformation(mask = '*') else VisualTransformation.None,
        suffix = {
            IconButton(
                modifier = Modifier
                    .size(20.dp),
                onClick = { onEyeButtonClick(isVisible) }
            ) {
                Icon(
                    imageVector = if (isVisible)Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = next
        ),
        isError = isError
    )
}

@Composable
fun SingleInputSection(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    supportingText: String,
    shape: Shape = RectangleShape,
    isError: Boolean = false
) {
    InputText(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        supportingText = supportingText,
        shape = shape,
        isError = isError
    )
}

@Composable
fun TwoInputSection(
    modifier: Modifier = Modifier,
    firstName: String,
    lastName: String,
    onFirstNameChange: KFunction1<AdminSignupEvent, Unit>,
    onLastNameChange: KFunction1<AdminSignupEvent, Unit>,
    supportingText1: String,
    supportingText2: String
) {
    Row(
        modifier = modifier
    ) {
        InputText(
            modifier = Modifier.weight(0.5f),
            value = firstName,
            onValueChange = { onFirstNameChange(AdminSignupEvent.OnFirstNameChange(it)) },
            supportingText = supportingText1,
            shape = RoundedCornerShape(topStart = 25.dp)
        )
        InputText(
            modifier = Modifier.weight(0.5f),
            value = lastName,
            onValueChange = { onLastNameChange(AdminSignupEvent.OnLastNameChange(it)) },
            supportingText = supportingText2,
            shape = RoundedCornerShape(topEnd = 25.dp)
        )
    }
}


@Composable
fun InputText(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    supportingText: String,
    shape: Shape = RectangleShape,
    isError: Boolean = false,
) {
    OutlinedTextField(modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        shape = shape,
        placeholder = { Text(text = supportingText, style = MaterialTheme.typography.titleMedium) },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        isError = isError
    )

}
@Composable
fun TopTitleAdminSignUp(
    text: String, size: TextUnit
) {
    Text(
        text = text, style = MaterialTheme.typography.displayMedium.copy(fontSize = size)
    )
}
