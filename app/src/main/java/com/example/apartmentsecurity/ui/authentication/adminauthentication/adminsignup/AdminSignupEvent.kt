package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup

sealed class AdminSignupEvent {
    data class OnFirstNameChange(val fName : String) : AdminSignupEvent()

    data class OnLastNameChange(val lName : String) : AdminSignupEvent()

    data class OnEmailChange(val email : String) : AdminSignupEvent()

    data class OnApartmentNameChange(val apartmentName : String) : AdminSignupEvent()

    data class OnUserNameChange(val userName : String) : AdminSignupEvent()

    data class OnPasswordChange(val password : String) :  AdminSignupEvent()

    data class OnConfirmPasswordChange(val confirmPassword : String) : AdminSignupEvent()

    data class OnPasswordVisibilityChange(val isPasswordVisible : Boolean) : AdminSignupEvent()

    data class OnConfirmPasswordVisibilityChange(val isConfirmPasswordVisible : Boolean ) : AdminSignupEvent()

}