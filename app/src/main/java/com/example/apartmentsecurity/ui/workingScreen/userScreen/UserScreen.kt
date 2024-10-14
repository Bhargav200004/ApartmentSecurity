package com.example.apartmentsecurity.ui.workingScreen.userScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apartmentsecurity.R

@Composable
fun UserScreen(modifier: Modifier = Modifier) {

    val viewmodel : UserScreenViewModel = hiltViewModel()


    Scaffold(
        topBar = {
            UserScreenTopBar()
        }
    ) {paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VisitorInformationCard()
            VisitorInformationCard()
            VisitorInformationCard()

        }
    }
}

@Composable
private fun VisitorInformationCard() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1/0.5f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Gujjla Bhargav sai Durga")
                    Text(
                        text = "For the Inform",
                        maxLines = 2
                    )
                    Text(text = "Room Number")
                }
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "In Time")
                Text(text = "Out Time")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = "User Screen")
        },
        navigationIcon = {
            IconButton(
                onClick = {},
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Back"
                )
            }
        }

    )
}