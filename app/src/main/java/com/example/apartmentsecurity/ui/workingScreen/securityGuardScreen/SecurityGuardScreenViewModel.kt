package com.example.apartmentsecurity.ui.workingScreen.securityGuardScreen

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.model.VisitorData
import com.example.apartmentsecurity.util.FirebaseUtils.getCurrentTimeStamp
import com.example.apartmentsecurity.util.SnackBarController
import com.example.apartmentsecurity.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityGuardScreenViewModel @Inject constructor(
    private val fireStore: FireStore,
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {


    private val _state = MutableStateFlow(SecurityGuardScreenData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SecurityGuardScreenData()
    )

    init {
        getApartmentData()
    }


    fun onEvent(event: SecurityGuardScreenEvent) {
        when(event){
            is SecurityGuardScreenEvent.OnNameChange -> onNameChange(event.name)
            is SecurityGuardScreenEvent.OnPhoneNumberChange -> onPhoneNumberChange(event.phoneNumber)
            is SecurityGuardScreenEvent.OnVehicleNumberChange -> onVehicleNumberChange(event.vehicleNumber)
            is SecurityGuardScreenEvent.OnRoomNumberChange -> onRoomNumberChange(event.roomNumber)
            is SecurityGuardScreenEvent.OnReasonChange -> onReasonChange(event.reason)
            is SecurityGuardScreenEvent.OnOtherChange -> onOtherChange(event.other)
            is SecurityGuardScreenEvent.OnPictureChange -> onPictureChange(event.picture)
            SecurityGuardScreenEvent.OnDialogConfirmClick -> onDialogConfirmClick()
            SecurityGuardScreenEvent.OnDialogDismissClick -> onDialogDismissClick()
            SecurityGuardScreenEvent.OnSubmit -> onSubmitButtonClick()
            is SecurityGuardScreenEvent.OnBottomSheetInputClick -> onBottomSheetInputClick()
            SecurityGuardScreenEvent.OnBottomSheetClick -> onBottomSheetClick()
            SecurityGuardScreenEvent.OnBottomSheetDismissClick -> onBottomSheetDismissClick()
        }
    }

    private fun onSubmitButtonClick() {
        viewModelScope.launch {
            try{
                val visitorData = VisitorData(
                    photo = "",
                    name = state.value.name,
                    roomNo = state.value.roomNumber,
                    phoneNumber = state.value.phoneNumber,
                    vehicleNumber = state.value.vehicleNumber,
                    reason = state.value.reason
                )
                fireStore.sendVisitorData(
                    apartmentId = state.value.apartmentId,
                    apartmentName = state.value.apartmentName,
                    timeStampId = getCurrentTimeStamp(),
                    visitorData = visitorData
                )
                SnackBarController.sendEvent(SnackBarEvent(message = "Successfully send"))
            }catch (e : Exception){
                SnackBarController.sendEvent(SnackBarEvent(message = "Something went ${e.message}"))
            }
        }
    }


    private fun getApartmentData(){
        viewModelScope.launch {
            try {
                val apartmentName = mySharedPreferenceDataStore.getApartmentName()
                val apartmentId = mySharedPreferenceDataStore.getApartmentId()
                _state.update {state->
                    state.copy(
                        apartmentName = apartmentName,
                        apartmentId = apartmentId
                    )
                }
            }
            catch (e :Exception){
                SnackBarController.sendEvent(SnackBarEvent(message = "Something went ${e.message}"))
            }
        }
    }

    private fun onNameChange(name: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    name = name
                )
            }
        }
    }

    private fun onPhoneNumberChange(phoneNumber: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    phoneNumber = phoneNumber
                )
            }
        }
    }

    private fun onVehicleNumberChange(vehicleNumber: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    vehicleNumber = vehicleNumber
                )
            }
        }
    }

    private fun onRoomNumberChange(roomNumber: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    roomNumber = roomNumber
                )
            }
        }
    }

    private fun onReasonChange(reason: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    reason = reason
                )
            }
            if (reason == "Other"){
                _state.update {state->
                    state.copy(
                        showDialog = true
                    )
                }
            }
        }
    }

    private fun onOtherChange(other: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    other = other
                )
            }
        }
    }

    private fun onPictureChange(picture: Bitmap?) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    pictureBitmap = picture
                )
            }
        }
    }

    private fun onDialogConfirmClick() {
        viewModelScope.launch {
            if (state.value.other.isNotEmpty()) {
                _state.update { state ->
                    state.copy(
                        reason = state.other,
                        showDialog = false,
                        other = ""
                    )
                }
            } else {
                SnackBarController.sendEvent(SnackBarEvent(message = "Reason is Empty"))
            }
        }
    }

    private fun onDialogDismissClick() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    showDialog = false,
                    reason = SecurityGuardScreenData.Companion.Reason.DELIVERY.value,
                    other = ""
                )
            }
        }
    }

    private fun onBottomSheetInputClick() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    showModalBottomSheet = !state.showModalBottomSheet
                )
            }
        }
    }

    private fun onBottomSheetDismissClick() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    showModalBottomSheet = false
                )
            }
        }
    }

    private fun onBottomSheetClick() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    showModalBottomSheet = true
                )
            }
        }
    }

}