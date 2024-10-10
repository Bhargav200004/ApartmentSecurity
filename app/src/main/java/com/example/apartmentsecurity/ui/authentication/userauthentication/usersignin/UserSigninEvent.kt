package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

sealed class UserSigninEvent {

    data class OnApartmentIdChange(val apartmentId : String) : UserSigninEvent()

    data class OnApartmentNameChange(val apartmentName : String) : UserSigninEvent()

    data class OnUserNameChange(val userName : String) : UserSigninEvent()

    data class OnEmailChange(val email : String) : UserSigninEvent()

    data class OnPasswordChange(val password : String) :  UserSigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : UserSigninEvent()

    data object OnSubmitButtonClick : UserSigninEvent()
}