package com.example.apartmentsecurity.ui.workingScreen.userScreen

sealed class UserScreenEvent {
    data class DataChange(val data: String) : UserScreenEvent()
}