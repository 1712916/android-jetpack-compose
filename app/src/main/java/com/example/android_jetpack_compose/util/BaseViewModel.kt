package com.example.android_jetpack_compose.util

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : MapStateViewModel() {
    private val _toastState = MutableSharedFlow<ShowToastMessage?>()
    val toastState = _toastState.asSharedFlow()

    val _loadingStateFlow = MutableStateFlow<Boolean>(false)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    suspend fun showLoading() {
        _loadingStateFlow.emit(true)
    }

    suspend fun hideLoading() {
        _loadingStateFlow.emit(false)
    }

    suspend fun emitToast(message: ShowToastMessage) {
        _toastState.emit(message)
    }

    fun launch(function: suspend () -> Unit) {
        viewModelScope.launch {
            function()
        }
    }
}
