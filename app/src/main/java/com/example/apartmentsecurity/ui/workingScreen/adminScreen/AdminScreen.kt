package com.example.apartmentsecurity.ui.workingScreen.adminScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apartmentsecurity.R
import com.example.apartmentsecurity.ui.navigation.AppScreen
import com.example.apartmentsecurity.ui.workingScreen.component.VisitorInformationCard

@Composable
fun AdminScreen(navController: NavController) {

    val viewModel: AdminScreenViewModel = hiltViewModel()

    val uiState by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            AdminScreenTopBar(
                onBackButtonClick = {
                    navController.popBackStack(route = AppScreen.MainScreen , inclusive = false)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                if (uiState.data.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.emptydata),
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    items(uiState.data) {
                        if (it.name != "") {
                            VisitorInformationCard(
                                name = it.name,
                                reason = it.reason,
                                phoneNumber = it.phoneNumber,
                                photo = it.photo,
                                date = it.date,
                                time = it.time
                            )
                        }
                    }
                }

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminScreenTopBar(
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Admin Screen")
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