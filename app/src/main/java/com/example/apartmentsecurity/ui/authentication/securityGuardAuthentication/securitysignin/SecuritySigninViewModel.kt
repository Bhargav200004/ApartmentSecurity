package com.example.apartmentsecurity.ui.authentication.securityGuardAuthentication.securitysignin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SecuritySigninViewModel @Inject constructor(
    private val authRepository: FirebaseAuthenticator
) : ViewModel() {

    private var _state = MutableStateFlow(SecuritySigninData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SecuritySigninData()
    )

    fun onEvent(event : SecuritySigninEvent){
        when(event){
            is SecuritySigninEvent.OnEmailChange -> onEmailChange(event.email)
            is SecuritySigninEvent.OnPasswordChange -> onPassword(event.password)
            is SecuritySigninEvent.OnPasswordVisibleChange -> onPasswordVisibleChange(event.show)
            SecuritySigninEvent.OnSubmitButtonClick -> onSubmitButtonClick()
        }
    }

    private fun onSubmitButtonClick() {
        viewModelScope.launch {
            try {
                authRepository.signInWithEmailPassword(
                    state.value.email,
                    state.value.password
                )

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

    private fun onPasswordVisibleChange(show: Boolean) {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    passwordVisible = !show
                )
            }
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