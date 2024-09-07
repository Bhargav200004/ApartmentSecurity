package com.example.apartmentsecurity.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


object SnackBarController {
    //channel is stream and event between SnackBarEvent or Class where we have to use
    private val _events = Channel<SnackBarEvent>()
    val event = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}


data class SnackBarAction(
    val label: String?,
    val action: () -> Unit
)

data class SnackBarEvent(
    val message: String,
    val actionLabel: SnackBarAction? = null
)