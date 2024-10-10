package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

data class UserSigninData (
    val apartmentId : String = "" ,
    val apartmentName : String = "" ,
    val userName : String = "" ,
    val email : String = "" ,
    val password : String = "",
    val passwordVisible : Boolean = false,
    val navigationApproval : Boolean = false
)