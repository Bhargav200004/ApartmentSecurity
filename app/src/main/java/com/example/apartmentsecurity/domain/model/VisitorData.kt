package com.example.apartmentsecurity.domain.model

data class VisitorData(
    val photo : String? = null,
    val name :  String,
    val roomNo : String,
    val phoneNumber : String,
    val vehicleNumber : String,
    val reason : String,
    val dateTime : String
)
