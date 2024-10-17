package com.example.apartmentsecurity.ui.workingScreen.userScreen

import com.example.apartmentsecurity.domain.model.UserDataModel

data class UserScreenData (
    val data : List<UserDataModel> = emptyList(),
    val roomNumber : String = ""
)