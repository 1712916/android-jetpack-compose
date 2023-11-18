package com.example.android_jetpack_compose.util

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    transform: (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, transform(value))
}

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    initialValue: K,
    transform: suspend (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, initialValue)
}

abstract class MapStateViewModel : ViewModel() {
    fun <T, K> StateFlow<T>.mapState(
        transform: (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            transform = transform
        )
    }

    fun <T, K> StateFlow<T>.mapState(
        initialValue: K,
        transform: suspend (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            initialValue = initialValue,
            transform = transform
        )
    }
}
