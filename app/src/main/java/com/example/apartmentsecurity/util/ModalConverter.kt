package com.example.apartmentsecurity.util

import com.example.apartmentsecurity.domain.model.UserDataModel
import com.google.firebase.firestore.DocumentSnapshot

fun List<DocumentSnapshot>.toModelData(): List<UserDataModel> {
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
            UserDataModel(
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