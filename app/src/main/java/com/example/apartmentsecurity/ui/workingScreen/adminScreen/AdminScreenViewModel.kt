package com.example.apartmentsecurity.ui.workingScreen.adminScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import com.example.apartmentsecurity.domain.FireStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminScreenViewModel @Inject constructor(
    private val firebase : FireStore,
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(AdminScreenData())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AdminScreenData()
    )

    fun onEvent(event: AdminScreenEvent) {
        when (event) {

            else -> {}
        }
    }

    init {
        getDataMultipleDocument()
    }

    private fun getDataMultipleDocument() {

        viewModelScope.launch {
            try {
                val apartmentId = mySharedPreferenceDataStore.getApartmentId()
                val apartmentName = mySharedPreferenceDataStore.getApartmentName()
                firebase.getUserData(apartmentId = apartmentId , apartmentName = apartmentName).collect {
                    _state.update {state ->
                        state.copy(
                            data = it
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("FireBaseData", "Error getting documents: ", e)
            }
        }
    }

}

