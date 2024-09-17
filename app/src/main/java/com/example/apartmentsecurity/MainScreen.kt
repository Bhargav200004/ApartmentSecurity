package com.example.apartmentsecurity

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.apartmentsecurity.ui.authentication.component.SubmitButton

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
                .padding(20.dp),
            painter = painterResource(id = if(!isSystemInDarkTheme()) R.drawable.logo else R.drawable.logo_dark),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .size(500.dp)
                .offset(x = width / 2.8f, y = height / 9f)
                .rotate(-40f),
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null
        )

        ConstraintLayout{
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (adminButton, userButton , securityButton , cameraImage) = createRefs()


                Image(
                    modifier = Modifier
                        .constrainAs(cameraImage) {
                           bottom.linkTo(parent.bottom , margin = 30.dp)
                        },
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = null
                )

                SubmitButton(
                    modifier = Modifier
                        .fillMaxWidth(1 / 1.8f)
                        .constrainAs(adminButton) {
                            bottom.linkTo(userButton.top, margin = 50.dp)
                            end.linkTo(parent.end, margin = 150.dp)
                        },
                    text = "admin",
                    onSubmitClick = { /*TODO*/ }
                )
                SubmitButton(
                    modifier = Modifier
                        .fillMaxWidth(1 / 1.8f)
                        .constrainAs(userButton) {
                            bottom.linkTo(securityButton.top, margin = 50.dp)
                            end.linkTo(parent.end, margin = 85.dp)
                        },
                    text = "user",
                    onSubmitClick = { /*TODO*/ }
                )
                SubmitButton(
                    modifier = Modifier
                        .fillMaxWidth(1 / 1.8f)
                        .constrainAs(securityButton) {
                            bottom.linkTo(parent.bottom, margin = 50.dp)
                            end.linkTo(parent.end, margin = 10.dp)
                        },
                    text = "securityGuard",
                    onSubmitClick = { /*TODO*/ }
                )

            }
        }
    }


}

