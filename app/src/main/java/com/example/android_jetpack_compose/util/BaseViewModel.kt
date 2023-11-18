package com.example.android_jetpack_compose.util

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : ViewModel() {
    private val _toastState = MutableSharedFlow<ShowToastMessage?>()
    val toastState = _toastState.asSharedFlow()
    fun emitToast(message: ShowToastMessage) {
        viewModelScope.launch {
            _toastState.emit(message)
        }
    }
}
