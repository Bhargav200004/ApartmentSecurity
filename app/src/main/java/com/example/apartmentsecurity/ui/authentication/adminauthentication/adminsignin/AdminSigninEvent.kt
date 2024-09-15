package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin

import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.AdminSignupEvent

sealed class AdminSigninEvent(){

    data class OnEmailChange(val email : String) : AdminSigninEvent()

    data class OnPasswordChange(val password : String) :  AdminSigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : AdminSigninEvent()

    data object OnSubmitButtonClick : AdminSigninEvent()
}