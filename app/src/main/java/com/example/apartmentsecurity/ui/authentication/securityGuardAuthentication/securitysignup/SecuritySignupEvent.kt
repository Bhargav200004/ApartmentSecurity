package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignup

sealed class SecuritySignupEvent {

    data class OnFirstNameChange(val fName : String) : SecuritySignupEvent()

    data class OnLastNameChange(val lName : String) : SecuritySignupEvent()

    data class OnEmailChange(val email : String) : SecuritySignupEvent()

    data class OnApartmentNameChange(val apartmentName : String) : SecuritySignupEvent()

    data class OnApartmentIdChange(val apartmentId : String) : SecuritySignupEvent()

    data class OnUserNameChange(val userName : String) : SecuritySignupEvent()

    data class OnPasswordChange(val password : String) :  SecuritySignupEvent()

    data class OnConfirmPasswordChange(val confirmPassword : String) : SecuritySignupEvent()

    data class OnPasswordVisibilityChange(val isPasswordVisible : Boolean) : SecuritySignupEvent()

    data class OnConfirmPasswordVisibilityChange(val isConfirmPasswordVisible : Boolean ) : SecuritySignupEvent()

    data object OnErrorChange : SecuritySignupEvent()

    data object OnSubmitClick : SecuritySignupEvent()

}