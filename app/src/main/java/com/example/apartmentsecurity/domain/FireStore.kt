package com.example.apartmentsecurity.domain

import com.google.firebase.firestore.FirebaseFirestore

interface FireStore {

    suspend fun create(collection : String ,  document : String)
}