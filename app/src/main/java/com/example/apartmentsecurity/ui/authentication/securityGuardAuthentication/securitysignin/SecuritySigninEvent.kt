package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin

sealed class SecuritySigninEvent {
    data class OnApartmentIdChange(val apartmentId : String) : SecuritySigninEvent()

    data class OnApartmentNameChange(val apartmentName : String) : SecuritySigninEvent()

    data class OnUserNameChange(val userName : String) : SecuritySigninEvent()

    data class OnEmailChange(val email : String) : SecuritySigninEvent()

    data class OnPasswordChange(val password : String) :  SecuritySigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : SecuritySigninEvent()

    data object OnSubmitButtonClick : SecuritySigninEvent()
}