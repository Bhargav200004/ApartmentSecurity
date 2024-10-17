package com.example.apartmentsecurity.ui.workingScreen.userScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.apartmentsecurity.R
import com.example.apartmentsecurity.domain.model.UserScreenModel
import com.example.apartmentsecurity.ui.navigation.AppScreen

@Composable
fun UserScreen(navController: NavController) {

    val viewModel : UserScreenViewModel = hiltViewModel()

    val uiState by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            UserScreenTopBar(
                onBackButtonClick = {
                    navController.popBackStack(route = AppScreen.MainScreen , inclusive = false)
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){

                if(uiState.data.isEmpty()){
                    item{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.emptydata),
                                contentDescription = null
                            )
                        }
                    }
                }else{
                    items(uiState.data){
                        if (it.name != ""){
                            VisitorInformationCard(it)
                        }
                    }
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
                        .height(120.dp)
                        .fillMaxWidth(1*0.3f),
                    contentAlignment = Alignment.Center
                ){



                    if (user.photo == "") {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Camera"
                        )
                    }else{

                        val imageLoader = ImageRequest.Builder(LocalContext.current)
                            .data(user.photo)
                            .crossfade(true)
                            .build()

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = imageLoader,
                            contentDescription = "Image from Firebase",
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
fun UserScreenTopBar(
    onBackButtonClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "User Screen")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackButtonClick()
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }

    )
}