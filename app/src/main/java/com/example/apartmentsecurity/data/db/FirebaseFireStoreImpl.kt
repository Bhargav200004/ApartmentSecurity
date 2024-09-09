package com.example.apartmentsecurity.data.db

import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.util.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseFireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore
): FireStore {
    override suspend fun create(collection: String , document : String) {
        val data = hashMapOf(
            "securityGuard" to "",
            "user" to "",
        )
        db.collection(collection).document(document).set(data).await()
    }
}