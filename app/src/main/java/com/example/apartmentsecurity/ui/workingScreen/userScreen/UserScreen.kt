package com.example.apartmentsecurity.ui.workingScreen.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserScreen(modifier: Modifier = Modifier) {

    val viewmodel : UserScreenViewModel = hiltViewModel()


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewmodel.getDataMultipleDocument("101")
        }) {
            Text(text = "Submit")
//            Log.d("FireBaseData" , "Data = ${viewmodel.data}")
        }
    }
}