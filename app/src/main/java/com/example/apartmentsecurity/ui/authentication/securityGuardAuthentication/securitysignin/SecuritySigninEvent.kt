package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin

sealed class SecuritySigninEvent {

    data class OnEmailChange(val email : String) : SecuritySigninEvent()

    data class OnPasswordChange(val password : String) :  SecuritySigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : SecuritySigninEvent()

    data object OnSubmitButtonClick : SecuritySigninEvent()
}