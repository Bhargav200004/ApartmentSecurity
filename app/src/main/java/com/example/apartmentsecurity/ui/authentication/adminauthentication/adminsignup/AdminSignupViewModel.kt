package com.example.apartmentsecurity.ui.authentication.adminauthentication.adminsignup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FireStore
import com.example.apartmentsecurity.domain.FirebaseAuthenticator
import com.example.apartmentsecurity.domain.model.AdminData
import com.example.apartmentsecurity.util.SnackBarController
import com.example.apartmentsecurity.util.SnackBarEvent
import com.example.apartmentsecurity.util.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AdminSignupViewModel @Inject constructor(
    private val authRepository : FirebaseAuthenticator,
    private val firebaseFireStore: FireStore,
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(AdminSignupData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = AdminSignupData()
    )

    fun onEvent(event: AdminSignupEvent) {
        when (event) {
            is AdminSignupEvent.OnFirstNameChange -> onFirstNameChange(event.fName)
            is AdminSignupEvent.OnLastNameChange -> onLastNameChange(event.lName)
            is AdminSignupEvent.OnEmailChange -> onEmailNameChange(event.email)
            is AdminSignupEvent.OnApartmentNameChange -> onApartmentNameChange(event.apartmentName)
            is AdminSignupEvent.OnApartmentIdChange -> onApartmentIdChange(event.apartmentId)
            is AdminSignupEvent.OnUserNameChange -> onUserNameChange(event.userName)
            is AdminSignupEvent.OnPasswordChange -> onPasswordChange(event.password)
            is AdminSignupEvent.OnConfirmPasswordChange -> onConfirmPasswordChange(event.confirmPassword)
            is AdminSignupEvent.OnPasswordVisibilityChange -> onPasswordVisibleChange(event.isPasswordVisible)
            is AdminSignupEvent.OnConfirmPasswordVisibilityChange -> onConfirmPasswordVisibleChange(event.isConfirmPasswordVisible)
            AdminSignupEvent.OnSubmitClick -> onSubmitButtonClick()
            AdminSignupEvent.OnErrorChange -> onErrorChange()
        }
    }

    fun onErrorChange() {
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    emailError = !validateEmail(),
                    passwordError = !validatePassword()
                )
            }
        }
    }

    private fun onSubmitButtonClick() {
        viewModelScope.launch {
            SnackBarController.sendEvent(
                event = SnackBarEvent(
                    message =
                    if(state.value.emailError && state.value.passwordError){
                        "${state.value.errorMessageEmail}\n${state.value.errorMessagePassword}"
                    }
                    else if (state.value.emailError)
                        state.value.errorMessageEmail
                    else if (state.value.passwordError)
                        state.value.errorMessagePassword
                    else {
                        circularProgressBarShow(show = true)
                        signUpWithEmailPassword()
                        delay(4000)
                        circularProgressBarShow(show = false)
                        state.value.firebaseError
                    }
                )
            )
        }
    }


    private fun createDatabase(){
        viewModelScope.launch {
            try {
                val adminData = AdminData(
                    fName = state.value.firstName,
                    lName = state.value.lastName,
                    userName = state.value.userName,
                    apartmentName = state.value.apartmentName,
                    apartmentId = state.value.apartmentId
                )
                if ( state.value.user?.user?.uid != null){
                    val user = authRepository.getUser()!!.uid
                    val apartmentName = state.value.apartmentName
//                    userUUID = state.value.user?.user?.uid!!
                    firebaseFireStore.createAdmin(
                        collection = state.value.apartmentId,
                        document = state.value.apartmentName,
                        adminData = adminData
                    )
                    Log.d("Checkingerror" , "user are registered number $user , $apartmentName")
                }
                else{
                    Log.d("Checkingerror" , "user are not registered number")
                }

            }catch (e : Exception){
                Log.e("Checkingerror" , e.message.toString())
            }
        }
    }

    private fun signUpWithEmailPassword() {
        viewModelScope.launch {
            try {
                val result = authRepository.signUpWithEmailPassword(state.value.email , state.value.password)
                _state.update {state ->
                    state.copy(
                        user = result,
                        firebaseError = "SignUp SuccessFully"
                    )
                }
                delay(4000)
                storingDataInThePhone()
                createDatabase()
                _state.update {state ->
                    state.copy(
                        navigationApproval = true
                    )
                }
            }
            catch (e : Exception){
                _state.update {state ->
                    state.copy(
                        firebaseError = e.message.toString()
                    )
                }
                Log.e("errorMessage" , "${e.message}" )
            }
        }
    }

    private suspend fun storingDataInThePhone() {
        viewModelScope.launch {
            mySharedPreferenceDataStore.onSend(
                name = state.value.firstName,
                apartmentId = state.value.apartmentId,
                apartmentName = state.value.apartmentName,
            )
        }
    }

    private fun onPasswordVisibleChange(passwordVisible: Boolean) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        isPasswordVisible = !passwordVisible
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onConfirmPasswordVisibleChange(confirmPasswordVisible: Boolean) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        isConfirmPasswordVisible = !confirmPasswordVisible
                    )
                }
            }
        } catch (e: Exception) {

            Log.e(TAG, "${e.message}")
        }
    }

    private fun onFirstNameChange(fName: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        firstName = fName
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onLastNameChange(lName: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        lastName = lName
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onEmailNameChange(email: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        email = email
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onApartmentNameChange(apartmentName: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        apartmentName = apartmentName
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onApartmentIdChange(apartmentId: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        apartmentId = apartmentId
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onUserNameChange(username: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        userName = username
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onPasswordChange(password: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        password = password
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }

    private fun onConfirmPasswordChange(confirmPassword: String) {
        try {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        confirmPassword = confirmPassword
                    )
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        }
    }




    private fun circularProgressBarShow(show : Boolean) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    circularProgressionBarShow = show
                )
            }
        }
    }

    private fun String.validateEmail() = when {
        isEmpty() -> ValidationResult.EMPTY_EMAIL
        !Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matcher(this).matches() -> ValidationResult.INVALID_EMAIL
        else -> ValidationResult.VALID
    }

    private fun String.validatePassword() = when {
        isEmpty() -> ValidationResult.EMPTY_PASSWORD
        length < 8 -> ValidationResult.SHORT_PASSWORD
        none { it.isUpperCase() } -> ValidationResult.NO_UPPERCASE
        none { it.isLowerCase() } -> ValidationResult.NO_LOWERCASE
        none { it.isDigit() } -> ValidationResult.NO_DIGIT
        state.value.password != state.value.confirmPassword -> ValidationResult.PASSWORD_AND_CONFIRM_PASSWORD_NOT_SAME
        else -> ValidationResult.VALID
    }

    private fun validateEmail() : Boolean{
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    errorMessageEmail = state.email.validateEmail().message.toString()
                )
            }
        }
        return state.value.errorMessageEmail == ""
    }

    private fun validatePassword() : Boolean {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    errorMessagePassword = state.password.validatePassword().message.toString()
                )
            }
        }
        return state.value.errorMessagePassword == ""
    }

}

