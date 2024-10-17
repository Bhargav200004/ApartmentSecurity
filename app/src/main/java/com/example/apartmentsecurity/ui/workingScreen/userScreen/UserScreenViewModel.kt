package com.example.apartmentsecurity.ui.workingScreen.userScreen

import android.util.Log
import android.util.Printer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.model.UserScreenModel
import com.example.apartmentsecurity.util.await
import com.example.apartmentsecurity.util.snapshotFlow
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebase : FireStore,
    private val sharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(UserScreenData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserScreenData()
    )

    fun onEvent(event: UserScreenEvent) {
        when (event) {

            else -> {}
        }
    }

    init {
        getRoomNumberData()
    }

    private fun getRoomNumberData() {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    roomNumber = sharedPreferenceDataStore.getRoomNumber() ?: ""
                )
            }
            delay(50)
            Log.d("RoomNumber" , state.value.roomNumber)
            getRoomDataMultipleDocumentRefreshData(state.value.roomNumber)
        }
    }


    fun getDataSingleDocument() {
        viewModelScope.launch {
//            /Raj100/Raj/ApartmentData/100/20241007005744/vistiorData
            val docRef = db.collection("/Raj100/Raj/ApartmentData/100/20241007005744")
                .document("vistiorData")

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("FireBaseData", "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("FireBaseData", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FireBaseData", "get failed with ", exception)
                }
        }
    }


    private fun getRoomDataMultipleDocumentRefreshData(roomNumber: String) {
        viewModelScope.launch {
            firebase.getRoomUserData(roomNumber).collect{
                _state.update { state ->
                    state.copy(
                        data = it
                    )
                }
            }
        }
    }


    fun getDataMultipleDocument(roomNumber: String = "") {
        viewModelScope.launch {

            try {
                val querySnapshot = db.collection("/Raj100/Raj/ApartmentData")
//                .whereEqualTo("roomNo", roomNumber) // Uncomment if filtering by room number is needed
                    .get()
                    .await()

                _state.update { state ->
                    state.copy(
                        data = querySnapshot.documents.toModelData()
                    )
                }
//                for (userData in data) {
//                    Log.d("FireBaseData", "UserScreenData => $userData")
//                }

            } catch (e: Exception) {
                Log.e("FireBaseData", "Error getting documents: ", e)
            }
        }
    }

}


fun List<DocumentSnapshot>.toModelData(): List<UserScreenModel> {
    return this.mapNotNull { document ->
        val docData = document.data
        if (docData != null) {
            // Extract and map the document fields to UserScreenData
            val name = docData["name"] as? String ?: ""
            val reason = docData["reason"] as? String ?: ""
            val phoneNumber = docData["phoneNumber"] as? String ?: ""
            val roomNo = docData["roomNo"] as? String ?: ""
            val dateTime = docData["dateTime"] as? String
                ?: "20241013050348" // Use default value if not present

            // Extract and format the date and time from dateTime
            val date = dateTime.substring(0, 8)
            val time = dateTime.substring(8)

            val formattedDate =
                "${date.substring(0, 4)}-${date.substring(4, 6)}-${date.substring(6, 8)}"
            val formattedTime =
                "${time.substring(0, 2)}:${time.substring(2, 4)}:${time.substring(4, 6)}"

            val photo = docData["photo"] as? String ?: ""

            // Return the mapped UserScreenData object
            UserScreenModel(
                name = name,
                reason = reason,
                phoneNumber = phoneNumber,
                roomNo = roomNo,
                date = formattedDate,
                time = formattedTime,
                photo = photo
            )
        } else {
            null
        }
    }
}