package com.example.android_jetpack_compose.ui.method.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MethodViewModel : BaseViewModel() {
    val methodRepository: MethodRepository = MethodRepositoryImpl()
    private val _inputState = MutableStateFlow("")
    val inputState: StateFlow<String> = _inputState.asStateFlow()
    fun updateInput(it: String) {
        _inputState.value = it
    }

    fun addMethod() {
        viewModelScope.launch {
            methodRepository.create(
                ExpenseMethod(
                    value = _inputState.value,
                    id = "",
                )
            ).onSuccess {
                _inputState.value = ""
                emitToast(SuccessToastMessage("Add method successfully"))
            }.onFailure {
                emitToast(FailureToastMessage("Add method failed"))
            }
        }
    }
}
