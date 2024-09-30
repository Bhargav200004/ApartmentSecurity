package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignup

import com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup.AdminSignupEvent


sealed class UserSignupEvent {
    data class OnFirstNameChange(val fName : String) : UserSignupEvent()

    data class OnLastNameChange(val lName : String) : UserSignupEvent()

    data class OnEmailChange(val email : String) : UserSignupEvent()

    data class OnApartmentNameChange(val apartmentName : String) : UserSignupEvent()

    data class OnApartmentIdChange(val apartmentId : String) : UserSignupEvent()

    data class OnUserNameChange(val userName : String) : UserSignupEvent()

    data class OnUserRoomNoChange(val roomNo : String) : UserSignupEvent()

    data class OnPasswordChange(val password : String) :  UserSignupEvent()

    data class OnConfirmPasswordChange(val confirmPassword : String) : UserSignupEvent()

    data class OnPasswordVisibilityChange(val isPasswordVisible : Boolean) : UserSignupEvent()

    data class OnConfirmPasswordVisibilityChange(val isConfirmPasswordVisible : Boolean ) : UserSignupEvent()

    data object OnSubmitClick : UserSignupEvent()
}