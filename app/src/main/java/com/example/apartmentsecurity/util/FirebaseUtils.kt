package com.example.apartmentsecurity.util

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

object FirebaseUtils {
    fun getCurrentTimeStamp() : String{
        val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val currentTime: Date = Calendar.getInstance().time
        val formattedDate = formatter.format(currentTime)
        return formattedDate
    }

    const val SECURITY_GUARD = "SecurityGuard"

    const val APARTMENT_HOUSE_NO = "ApartmentHouseNo"

    const val APARTMENT_DATA = "ApartmentData"
}


