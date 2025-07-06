package com.example.apartmentsecurity.domain

import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.domain.model.SecurityData
import com.example.apartmentsecurity.domain.model.UserData
import com.example.apartmentsecurity.domain.model.UserDataModel
import com.example.apartmentsecurity.domain.model.VisitorData
import kotlinx.coroutines.flow.Flow

interface FireStore {

    suspend fun createAdmin(apartmentId : String, apartmentName : String, adminData: AdminData )

    suspend fun createUser(apartmentId : String, apartmentName : String, roomNo: String, userData: UserData)

    suspend fun createSecurity(apartmentId : String, apartmentName : String, securityUserName: String, securityData: SecurityData)

    suspend fun sendVisitorData(apartmentId: String,apartmentName: String,timeStampId : String,visitorData: VisitorData)

    fun getRoomUserData(apartmentId: String, apartmentName: String ,  roomNumber : String) : Flow<List<UserDataModel>>

    fun getUserData(apartmentId : String , apartmentName: String): Flow<List<UserDataModel>>
}




