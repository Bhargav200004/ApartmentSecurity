package com.example.apartmentsecurity.domain.model

import android.graphics.Bitmap

interface FireStorage {

    suspend fun onSendVisitorImageToStorage(apartmentId : String , apartmentName : String , visitorName : String , image : Bitmap)

    suspend fun onReceiveVisitorImageFromStorage(apartmentId : String , apartmentName: String, visitorName : String) : String

}