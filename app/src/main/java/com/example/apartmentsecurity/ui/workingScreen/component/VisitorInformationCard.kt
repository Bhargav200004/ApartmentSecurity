package com.example.apartmentsecurity.ui.workingScreen.component

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun VisitorInformationCard(
    name : String,
    reason : String,
    phoneNumber : String,
    photo : String,
    date : String,
    time : String
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
                    Text(text = name)
                    Text(
                        text = reason,
                        maxLines = 2
                    )
                    Text(text = phoneNumber)
                }
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(1*0.3f),
                    contentAlignment = Alignment.Center
                ){



                    if (photo == "") {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Camera"
                        )
                    }else{

                        val imageLoader = ImageRequest.Builder(LocalContext.current)
                            .data(photo)
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
                Text(text = date)
                Text(text = time)
            }
        }
    }
}