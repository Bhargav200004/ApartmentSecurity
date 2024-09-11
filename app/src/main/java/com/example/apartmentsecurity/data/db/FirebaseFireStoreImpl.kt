package com.example.apartmentsecurity.data.db


import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.util.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseFireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore
): FireStore {
    override suspend fun create(
        collection: String ,
        document : String ,
        adminData: AdminData
    )  {
        db.collection(collection).document(document).set(adminData).await()
    }
}