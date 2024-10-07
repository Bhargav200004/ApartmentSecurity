package com.example.apartmentsecurity.ui.workingScreen.userScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.util.await
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {
//    var data by mutableStateOf("")


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

    fun getDataMultipleDocument(roomNumber : String){
        viewModelScope.launch {
//            val docRef = db.collection("/Raj100/Raj/ApartmentData")
//
//                docRef.get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
////                        Log.d("FireBaseData", "${document.id} => ${document.data}")
//                        if (document.data["RoomNumber"] == roomNumber) {
//                            Log.d("FireBaseData", "${document.id} => ${document.data}")
//                        }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d("FireBaseData", "Error getting documents: ", exception)
//                }
            try {
                // Directly query for documents where RoomNumber matches the given roomNumber
                val querySnapshot = db.collection("/Raj100/Raj/ApartmentData")
                    .whereEqualTo("roomNo", roomNumber) // Filter by room number
                    .get()
                    .await() // Suspend the coroutine until the data is fetched

                // Loop through the results and log the documents
                for (document in querySnapshot.documents) {
                    Log.d("FireBaseData", "${document.id} => ${document.data}")
                }

            } catch (e: Exception) {
                Log.e("FireBaseData", "Error getting documents: ", e)
            }
        }
    }

}