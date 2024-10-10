package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin

sealed class AdminSigninEvent(){

    data class OnApartmentIdChange(val apartmentId : String) : AdminSigninEvent()

    data class OnApartmentNameChange(val apartmentName : String) : AdminSigninEvent()

    data class OnUserNameChange(val userName : String) : AdminSigninEvent()

    data class OnEmailChange(val email : String) : AdminSigninEvent()

    data class OnPasswordChange(val password : String) :  AdminSigninEvent()

    data class OnPasswordVisibleChange(val show : Boolean) : AdminSigninEvent()

    data object OnSubmitButtonClick : AdminSigninEvent()
}