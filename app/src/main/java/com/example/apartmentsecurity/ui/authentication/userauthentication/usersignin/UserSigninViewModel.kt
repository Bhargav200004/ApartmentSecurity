package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.AuthenticationType
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FirebaseAuthenticator
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
class UserSigninViewModel @Inject constructor(
    private val authRepository: FirebaseAuthenticator,
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(UserSigninData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = UserSigninData()
    )

    fun onEvent(event : UserSigninEvent){
        when(event){
            is UserSigninEvent.OnApartmentIdChange -> onApartmentIdChange(event.apartmentId)
            is UserSigninEvent.OnApartmentNameChange -> onApartmentNameChange(event.apartmentName)
            is UserSigninEvent.OnUserNameChange -> onUserNameChange(event.userName)
            is UserSigninEvent.OnEmailChange -> onEmailChange(event.email)
            is UserSigninEvent.OnPasswordChange -> onPassword(event.password)
            is UserSigninEvent.OnPasswordVisibleChange -> onPasswordVisibleChange(event.show)
            UserSigninEvent.OnSubmitButtonClick -> onSubmitButtonClick()

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
                //User1@gmail.com
                //Filmmaker2004#
                Log.d("SignIn","Success")
            }
            catch (e : Exception){
                SnackBarController.sendEvent(SnackBarEvent(message = "Email and password not match"))
                Log.e("SignIn",e.message.toString())
            }
        }
    }


    private fun onUserNameChange(userName: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    userName = userName
                )
            }
        }
    }

    private fun onApartmentNameChange(apartmentName: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    apartmentName = apartmentName
                )
            }
        }
    }

    private fun onApartmentIdChange(apartmentId: String) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    apartmentId = apartmentId
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

    private suspend fun storingDataInThePhone() {
        viewModelScope.launch {
            mySharedPreferenceDataStore.onSendAuthenticationType(
                authenticationType = AuthenticationType.USER.name
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