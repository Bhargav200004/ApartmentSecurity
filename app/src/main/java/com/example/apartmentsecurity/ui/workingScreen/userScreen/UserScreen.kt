package com.example.apartmentsecurity.ui.workingScreen.userScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.apartmentsecurity.R

@Composable
fun UserScreen(modifier: Modifier = Modifier) {

    val viewModel : UserScreenViewModel = hiltViewModel()

    val uiState by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            UserScreenTopBar()
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn (
                modifier = modifier
                    .padding(paddingValues = paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(uiState.data){
                    VisitorInformationCard(it)
                }
            }
        }
    }
}

@Composable
private fun VisitorInformationCard(
    user: UserScreenModel
) {
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
                    Text(text = user.name)
                    Text(
                        text = user.reason,
                        maxLines = 2
                    )
                    Text(text = user.phoneNumber)
                }
                Box(
                    modifier = Modifier
                        .size(200.dp)
                ){
                    if (user.photo == "") {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Camera"
                        )
                    }else{
                        AsyncImage(
                            model = user.photo,
                            contentDescription = "Image from Firebase",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds // Adjust as needed
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = user.date)
                Text(text = user.time)
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