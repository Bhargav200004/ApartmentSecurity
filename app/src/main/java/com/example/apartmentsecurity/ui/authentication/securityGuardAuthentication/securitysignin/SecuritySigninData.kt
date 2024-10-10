package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin

data class SecuritySigninData(
    val apartmentId : String = "" ,
    val apartmentName : String = "" ,
    val userName : String = "" ,
    val email : String = "" ,
    val password : String = "",
    val passwordVisible : Boolean = false,
    val navigationApproval : Boolean = false
)