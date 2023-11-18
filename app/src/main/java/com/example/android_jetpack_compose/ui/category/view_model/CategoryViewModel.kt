package com.example.android_jetpack_compose.ui.expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CategoryViewModel : BaseViewModel() {
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    var categoryList: LiveData<List<ExpenseCategory>> = categoryRepository.getLiveDataList()
    private val _inputState = MutableStateFlow("")
    val inputState: StateFlow<String> = _inputState.asStateFlow()
    fun updateInput(it: String) {
        _inputState.value = it
    }

    fun addCategory() {
        if (_inputState.value.isBlank()) {
            return
        }


        viewModelScope.launch {
            categoryRepository.create(
                ExpenseCategory(
                    name = _inputState.value,
                    id = -1,
                )
            ).onSuccess {
                _inputState.value = ""
                emitToast(SuccessToastMessage("Add category successfully"))
            }.onFailure {
                emitToast(FailureToastMessage("Add category failed"))
            }
        }
    }
}
