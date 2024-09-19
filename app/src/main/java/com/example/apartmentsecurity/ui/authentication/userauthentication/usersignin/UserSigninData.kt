package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

data class UserSigninData (
    val email : String = "" ,
    val password : String = "",
    val passwordVisible : Boolean = false,
    val navigationApproval : Boolean = false
)