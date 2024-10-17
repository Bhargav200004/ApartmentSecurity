package com.example.apartmentsecurity.ui.workingScreen.userScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FireStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val firebase : FireStore,
    private val sharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(UserScreenData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserScreenData()
    )

    fun onEvent(event: UserScreenEvent) {
        when (event) {

            else -> {}
        }
    }

    init {
        getRoomNumberData()
    }

    private fun getRoomNumberData() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    roomNumber = sharedPreferenceDataStore.getRoomNumber() ?: ""
                )
            }
            delay(50)
            Log.d("RoomNumber", state.value.roomNumber)
            getRoomDataMultipleDocumentRefreshData(state.value.roomNumber)
        }
    }

    private fun getRoomDataMultipleDocumentRefreshData(roomNumber: String) {
        viewModelScope.launch {
            firebase.getRoomUserData(roomNumber).collect {
                _state.update { state ->
                    state.copy(
                        data = it
                    )
                }
            }
        }
    }
}


