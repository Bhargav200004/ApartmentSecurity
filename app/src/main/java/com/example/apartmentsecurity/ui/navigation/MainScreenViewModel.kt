package com.example.apartmentsecurity.ui.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentsecurity.AuthenticationType
import com.example.apartmentsecurity.MySharedPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel@Inject constructor(
    private val mySharedPreferenceDataStore: MySharedPreferenceDataStore
) : ViewModel() {

    private var _state = MutableStateFlow(AuthenticationType.UNAUTHENTICATED.name)
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = AuthenticationType.UNAUTHENTICATED.name
    )

    init {
        getAuthenticationType()
    }

    private fun getAuthenticationType(){
        viewModelScope.launch {
            try {
                mySharedPreferenceDataStore.preferenceDataFlow.map {
                    it.authenticationType
                }.collect{authenticationType->
                    _state.value = authenticationType.name
                    Log.d("TAG" , _state.value)
                }
            }
            catch (e :Exception){
                Log.d("TAG" , e.message.toString())
            }
        }
    }
}