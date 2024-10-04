package com.example.apartmentsecurity.domain

import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.domain.model.SecurityData
import com.example.apartmentsecurity.domain.model.UserData
import com.example.apartmentsecurity.domain.model.VisitorData
import com.google.firebase.firestore.DocumentReference

interface FireStore {

    suspend fun createAdmin(collection : String, document : String, adminData: AdminData )

    suspend fun createUser(collection : String, document : String ,roomNo: String,userData: UserData)

    suspend fun createSecurity(collection : String, document : String ,securityUserName: String ,securityData: SecurityData)

    suspend fun sendSecurityData(reference : String, roomNo: String , visitorData: VisitorData)
}

