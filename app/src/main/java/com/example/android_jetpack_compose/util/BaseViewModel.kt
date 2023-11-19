package com.example.android_jetpack_compose.util

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : MapStateViewModel() {
    private val _toastState = MutableSharedFlow<ShowToastMessage?>()
    val toastState = _toastState.asSharedFlow()
    suspend fun emitToast(message: ShowToastMessage) {
        _toastState.emit(message)
    }
}
