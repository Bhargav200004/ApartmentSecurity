package com.example.apartmentsecurity.util

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resumeWithException


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}


fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listenerRegistration: ListenerRegistration = addSnapshotListener { snapshot, exception ->
        if (exception != null) {
            // Close the flow if there's an error
            close(exception)
        } else if (snapshot != null) {
            // Emit the new snapshot
            trySend(snapshot).isSuccess
        }
    }

    awaitClose { listenerRegistration.remove() }
}

object FirebaseUtils {
    fun getCurrentTimeStamp() : String{
        val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val currentTime: Date = Calendar.getInstance().time
        val formattedDate = formatter.format(currentTime)
        return formattedDate
    }

    const val SECURITY_GUARD :  String = "SecurityGuard"

    const val APARTMENT_HOUSE_NO : String = "ApartmentHouseNo"

    const val APARTMENT_DATA : String= "ApartmentData"
}


