package com.example.apartmentsecurity.data.storage

import android.graphics.Bitmap
import com.example.apartmentsecurity.domain.model.FireStorage
import com.example.apartmentsecurity.util.await
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseStorageImpl @Inject constructor(
    private val storage : FirebaseStorage
) : FireStorage {

    override suspend fun onSendVisitorImageToStorage(
        apartmentId: String,
        apartmentName: String,
        visitorName: String,
        image: Bitmap
    )  {
        val fileName = "${apartmentName}_${visitorName}.jpg"
        val baos = ByteArrayOutputStream()

        image.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val data = baos.toByteArray()

        val imageRef = storage.reference.child(apartmentId)

        imageRef.child(fileName).putBytes(data).await()
    }

    override suspend fun onReceiveVisitorImageFromStorage(
        apartmentId: String,
        apartmentName : String,
        visitorName: String
    ): String {

        val fileName = "${apartmentName}_${visitorName}.jpg"
        val imageRef = storage.reference.child(apartmentId).child(fileName)

        return imageRef.downloadUrl.await().toString()

    }
}