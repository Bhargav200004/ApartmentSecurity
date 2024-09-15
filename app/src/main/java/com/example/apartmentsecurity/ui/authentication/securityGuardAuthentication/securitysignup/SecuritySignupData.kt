package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignup

import com.google.firebase.auth.AuthResult

data class SecuritySignupData(
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val apartmentName : String = "",
    val userName : String = "",
    val password : String = "",
    val confirmPassword : String = "",
    val passwordError : Boolean = false,
    val emailError : Boolean = false,
    val isPasswordVisible : Boolean = false,
    val isConfirmPasswordVisible : Boolean = false,
    val errorMessageEmail: String = "",
    val errorMessagePassword : String = "",
    val user : AuthResult? = null,
    val circularProgressionBarShow : Boolean = false
)