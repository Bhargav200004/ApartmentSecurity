package com.example.apartmentsecurity.data.db


import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.domain.model.SecurityData
import com.example.apartmentsecurity.domain.model.UserData
import com.example.apartmentsecurity.domain.model.VisitorData
import com.example.apartmentsecurity.util.APARTMENT_HOUSE_NO
import com.example.apartmentsecurity.util.SECURITY_GUARD
import com.example.apartmentsecurity.util.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseFireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore
): FireStore {
    override suspend fun createAdmin(
        collection: String ,
        document : String ,
        adminData: AdminData
    )  {
        db.collection(collection).document(document).set(adminData).await()
        val guardRef = db.collection(collection).document(document)
        guardRef.collection(SECURITY_GUARD).document("EmptyData").set("" to "").await()
        guardRef.collection(APARTMENT_HOUSE_NO).document("EmptyData").set("" to "").await()
    }

    override suspend fun createUser(
        collection: String,
        document: String,
        roomNo: String,
        userData: UserData
    ) {
        val userRef = db.collection(collection).document(document).collection(APARTMENT_HOUSE_NO)
        userRef.document(roomNo).set(userData).await()
    }

    override suspend fun createSecurity(
        collection: String,
        document: String,
        securityUserName: String,
        securityData: SecurityData
    ) {
        val userRef = db.collection(collection).document(document).collection(SECURITY_GUARD)
        userRef.document(securityUserName).set(securityData).await()
    }

    override suspend fun sendSecurityData(
        reference : String,
        roomNo: String,
        visitorData: VisitorData
    ) {
        TODO("Not yet implemented")
    }


}