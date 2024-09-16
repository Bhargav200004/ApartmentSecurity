package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin.AdminSigninEvent

sealed class UserSigninEvent {

    data class OnEmailChange(val email : String) : UserSigninEvent()

    data class OnPasswordChange(val password : String) :  UserSigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : UserSigninEvent()

    data object OnSubmitButtonClick : UserSigninEvent()
}