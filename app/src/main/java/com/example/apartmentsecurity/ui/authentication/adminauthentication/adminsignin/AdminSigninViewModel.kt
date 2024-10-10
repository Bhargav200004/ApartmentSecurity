package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.AuthenticationType
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.data.authentication.FirebaseAuthenticatorImpl
import com.example.apartmentsecurity.util.SnackBarController
import com.example.apartmentsecurity.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminSigninViewModel @Inject constructor(
    private val authRepository: FirebaseAuthenticatorImpl,
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
): ViewModel() {

    private var _state = MutableStateFlow(AdminSigninData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = AdminSigninData()
    )

    fun onEvent(event : AdminSigninEvent){
        when(event){
            is AdminSigninEvent.OnApartmentIdChange -> onApartmentIdChange(event.apartmentId)
            is AdminSigninEvent.OnApartmentNameChange -> onApartmentNameChange(event.apartmentName)
            is AdminSigninEvent.OnUserNameChange -> onUserNameChange(event.userName)
            is AdminSigninEvent.OnEmailChange -> onEmailChange(event.email)
            is AdminSigninEvent.OnPasswordChange -> onPassword(event.password)
            is AdminSigninEvent.OnPasswordVisibleChange -> onPasswordVisibleChange(event.show)
            AdminSigninEvent.OnSubmitButtonClick -> onSubmitButtonClick()
        }
    }




    private fun onApartmentIdChange(apartmentId: String) {
            viewModelScope.launch {
                _state.update {state ->
                    state.copy(
                        apartmentId = apartmentId
                    )
                }
            }
    }

    private fun onApartmentNameChange(apartmentName: String) {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    apartmentName = apartmentName
                )
            }
        }
    }

    private fun onUserNameChange(userName: String) {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    userName = userName
                )
            }
        }
    }

    private fun onPasswordVisibleChange(show: Boolean) {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    passwordVisible = !show
                )
            }
        }
    }

    private fun onSubmitButtonClick() {
        viewModelScope.launch {
            try {
                authRepository.signInWithEmailPassword(
                    state.value.email,
                    state.value.password
                )
                storingDataInThePhone()
                SnackBarController.sendEvent(SnackBarEvent(message = "Successfully Login"))
                _state.update { state ->
                    state.copy(
                        navigationApproval = true
                    )
                }
                //dummy1@gmail.com
                //Filmmaker2004#
                Log.d("SignIn","Success")
            }
            catch (e : Exception){
                SnackBarController.sendEvent(SnackBarEvent(message = "Email and password not match"))
                Log.e("SignIn",e.message.toString())
            }

        }
    }

    private suspend fun storingDataInThePhone() {
        viewModelScope.launch {
            mySharedPreferenceDataStore.onSendAuthenticationType(
                authenticationType = AuthenticationType.ADMIN.name
            )
            mySharedPreferenceDataStore.onSend(
                name = state.value.userName,
                apartmentId = state.value.apartmentId,
                apartmentName = state.value.apartmentName
            )
        }
    }

    private fun onPassword(password: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    password = password
                )
            }
        }
    }


    private fun onEmailChange(event: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    email = event
                )
            }
        }
    }

}