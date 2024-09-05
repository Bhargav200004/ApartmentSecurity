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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminSignup(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {AdminSignUpTopBar()}
    ) { paddingValues ->
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

            SignupFormSection()

            SubmitButton()

            TextClickable()


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSignUpTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {}) {
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
fun SubmitButton(modifier: Modifier = Modifier) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        border = BorderStroke(1.dp, color = ButtonDefaults.outlinedButtonColors().contentColor),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = ButtonDefaults.outlinedButtonColors().contentColor
        )
    ) {
        TopTitleAdminSignUp(text = "Submit", size = 20.sp)
    }
}

@Composable
fun TextClickable(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        val darkThemeOrNot = isSystemInDarkTheme()
        Text(text = "Already have Account? ")
        Text(modifier = Modifier
            .drawBehind {
                    drawLine(
                        color = if(!darkThemeOrNot) Color(0xFF5A3D00) else Color(0xFFFFDEAB),
                        start = Offset(x = 0f , y = size.height * 0.8f ),
                        end = Offset(x = size.width , y = size.height * 0.8f  ),
                        strokeWidth = 2.dp.toPx()
                    )
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {

            }, text = "Log In" ,

        )
    }

}

@Composable
fun SignupFormSection(modifier: Modifier = Modifier) {
    Column {
        TwoInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText1 = "FIRST NAME",
            supportingText2 = "LAST NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText = "EMAIL ADDRESS"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText = "APARTMENT NAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText = "USERNAME"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText = "PASSWORD"
        )
        SingleInputSection(
            modifier = Modifier.fillMaxWidth(),
            supportingText = "CONFIRM PASSWORD"
        )
    }

}


@Composable
fun SingleInputSection(
    modifier: Modifier = Modifier, supportingText: String
) {
    InputText(
        modifier = modifier, supportingText = supportingText
    )
}

@Composable
fun TwoInputSection(
    modifier: Modifier = Modifier, supportingText1: String, supportingText2: String
) {
    Row(
        modifier = modifier
    ) {
        InputText(
            modifier = Modifier.weight(0.5f), supportingText = supportingText1
        )
        InputText(
            modifier = Modifier.weight(0.5f), supportingText = supportingText2
        )
    }
}


@Composable
fun InputText(
    modifier: Modifier = Modifier, supportingText: String
) {
    OutlinedTextField(modifier = modifier,
        value = "",
        onValueChange = {},
        shape = RectangleShape,
        placeholder = { Text(text = supportingText, style = MaterialTheme.typography.titleMedium) })
}

@Composable
fun TopTitleAdminSignUp(
    text: String, size: TextUnit
) {
    Text(
        text = text, style = MaterialTheme.typography.displayMedium.copy(fontSize = size)
    )
}
