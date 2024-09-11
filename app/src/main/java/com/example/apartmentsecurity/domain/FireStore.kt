package com.example.apartmentsecurity.domain

import com.example.apartmentsecurity.domain.model.AdminData
import com.google.firebase.firestore.DocumentReference

interface FireStore {

    suspend fun create(collection : String ,  document : String , adminData: AdminData )

}

