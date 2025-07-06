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
        apartmentId: String,
        apartmentName: String,
        adminData: AdminData
    ) {
        db.collection(apartmentId).document(apartmentName).set(adminData).await()
        val apartmentDataReference = db.collection(apartmentId).document(apartmentName)
        apartmentDataReference.collection(SECURITY_GUARD).document("EmptyData").set("" to "")
            .await()
        apartmentDataReference.collection(APARTMENT_HOUSE_NO).document("EmptyData").set("" to "")
            .await()
        apartmentDataReference.collection(APARTMENT_DATA).document("EmptyData").set("" to "")
            .await()
    }

    override suspend fun createUser(
        apartmentId: String,
        apartmentName: String,
        roomNo: String,
        userData: UserData
    ) {
        val userRef = db.collection(apartmentId).document(apartmentName).collection(APARTMENT_HOUSE_NO)
        userRef.document(roomNo).set(userData).await()
    }

    override suspend fun createSecurity(
        apartmentId: String,
        apartmentName: String,
        securityUserName: String,
        securityData: SecurityData
    ) {
        val securityRef = db.collection(apartmentId).document(apartmentName).collection(SECURITY_GUARD)
        securityRef.document(securityUserName).set(securityData).await()
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

    override fun getRoomUserData(apartmentId: String, apartmentName: String ,roomNumber: String): Flow<List<UserDataModel>> {
        // Listen for real-time updates with addSnapshotListener
        return db.collection("/${apartmentId}/${apartmentName}/ApartmentData")
            .whereEqualTo("roomNo", roomNumber) // Uncomment if filtering by room number is needed
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.toModelData()
            }
    }

    override fun getUserData(apartmentId : String , apartmentName: String ): Flow<List<UserDataModel>> {
        // Listen for real-time updates with addSnapshotListener
        return db.collection("/${apartmentId}/${apartmentName}/ApartmentData")
            .snapshotFlow()
            .map { snapshot ->
                snapshot.documents.toModelData()
            }
    }
}