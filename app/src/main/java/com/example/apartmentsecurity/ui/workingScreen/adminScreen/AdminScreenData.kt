package com.example.apartmentsecurity.ui.workingScreen.adminScreen

import com.example.apartmentsecurity.domain.model.UserDataModel

data class AdminScreenData (
    val data : List<UserDataModel> = emptyList(),
)