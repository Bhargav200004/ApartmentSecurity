package com.example.apartmentsecurity.ui.workingScreen.userScreen

import com.example.apartmentsecurity.domain.model.UserScreenModel

data class UserScreenData (
    val data : List<UserScreenModel> = emptyList(),
    val roomNumber : String = ""
)