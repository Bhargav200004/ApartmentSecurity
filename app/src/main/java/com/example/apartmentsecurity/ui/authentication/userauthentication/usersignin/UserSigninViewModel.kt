package com.example.apartmentsecurity.ui.authentication.userauthentication.usersignin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.util.SnackBarController
import com.example.apartmentsecurity.util.SnackBarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserSigninViewModel : ViewModel() {

    private var _state = MutableStateFlow(UserSigninData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = UserSigninData()
    )

    fun onEvent(event : UserSigninEvent){
        when(event){
            is UserSigninEvent.OnEmailChange -> onEmailChange(event.email)
            is UserSigninEvent.OnPasswordChange -> onPassword(event.password)
            is UserSigninEvent.OnPasswordVisibleChange -> onPasswordVisibleChange(event.show)
            UserSigninEvent.OnSubmitButtonClick -> onSubmitButtonClick()
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
//                authRepository.signInWithEmailPassword(
//                    state.value.email,
//                    state.value.password
//                )
                SnackBarController.sendEvent(SnackBarEvent(message = "Successfully Login"))
                Log.d("SignIn","Success")
            }
            catch (e : Exception){
                SnackBarController.sendEvent(SnackBarEvent(message = "Email and password not match"))
                Log.e("SignIn",e.message.toString())
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