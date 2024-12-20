package com.example.apartmentsecurity.data.db


import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.domain.model.SecurityData
import com.example.apartmentsecurity.domain.model.UserData
import com.example.apartmentsecurity.domain.model.UserDataModel
import com.example.apartmentsecurity.domain.model.VisitorData
import com.example.apartmentsecurity.util.FirebaseUtils.APARTMENT_DATA
import com.example.apartmentsecurity.util.FirebaseUtils.APARTMENT_HOUSE_NO
import com.example.apartmentsecurity.util.FirebaseUtils.SECURITY_GUARD
import com.example.apartmentsecurity.util.await
import com.example.apartmentsecurity.util.snapshotFlow
import com.example.apartmentsecurity.util.toModelData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseFireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FireStore {

    override suspend fun createAdmin(
        collection: String,
        document: String,
        adminData: AdminData
    ) {
        db.collection(collection).document(document).set(adminData).await()
        val apartmentDataReference = db.collection(collection).document(document)
        apartmentDataReference.collection(SECURITY_GUARD).document("EmptyData").set("" to "")
            .await()
        apartmentDataReference.collection(APARTMENT_HOUSE_NO).document("EmptyData").set("" to "")
            .await()
        apartmentDataReference.collection(APARTMENT_DATA).document("EmptyData").set("" to "")
            .await()
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

    override suspend fun sendVisitorData(
        apartmentId: String,
        apartmentName: String,
        timeStampId: String,
        visitorData: VisitorData
    ) {
//        val docRef = db.collection("/Raj100/Raj/ApartmentData")
//        /Raj100/Raj/ApartmentData/20241007032452
//        ApartmentData
        val apartmentData =
            db.collection(apartmentId).document(apartmentName).collection(APARTMENT_DATA)
                .document(timeStampId)
        apartmentData.set(visitorData).await()
    }

    override fun getRoomUserData(roomNumber: String): Flow<List<UserDataModel>> {
        // Listen for real-time updates with addSnapshotListener
        return db.collection("/Raj100/Raj/ApartmentData")
            .whereEqualTo("roomNo", roomNumber) // Uncomment if filtering by room number is needed
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.toModelData()
            }
    }

    override fun getUserData(): Flow<List<UserDataModel>> {
        // Listen for real-time updates with addSnapshotListener
        return db.collection("/Raj100/Raj/ApartmentData")
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.toModelData()
            }
    }
}