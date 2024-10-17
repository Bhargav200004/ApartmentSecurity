package com.example.apartmentsecurity.domain.model

data class UserScreenModel(
    val name: String,
    val reason: String,
    val phoneNumber: String,
    val roomNo: String,
    val date: String,
    val time: String,
    val photo: String
)