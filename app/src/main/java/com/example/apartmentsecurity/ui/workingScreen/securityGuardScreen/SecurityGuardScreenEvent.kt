package com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen

sealed class SecurityGuardScreenEvent {

    data class OnNameChange(val name: String) : SecurityGuardScreenEvent()

    data class OnPhoneNumberChange(val phoneNumber: String) : SecurityGuardScreenEvent()

    data class OnVehicleNumberChange(val vehicleNumber: String) : SecurityGuardScreenEvent()

    data class OnRoomNumberChange(val roomNumber: String) : SecurityGuardScreenEvent()

    data object OnBottomSheetInputClick : SecurityGuardScreenEvent()

    data class OnReasonChange(val reason: String) : SecurityGuardScreenEvent()

    data object OnBottomSheetClick : SecurityGuardScreenEvent()

    data object OnBottomSheetDismissClick : SecurityGuardScreenEvent()

    data object OnSubmit : SecurityGuardScreenEvent()

}