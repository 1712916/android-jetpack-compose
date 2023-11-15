package com.example.android_jetpack_compose.data

import androidx.lifecycle.*
import com.example.android_jetpack_compose.entity.*

interface LiveDataList<T> {
    fun getLiveDataList(): LiveData<List<T>>
}
