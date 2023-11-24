package com.example.android_jetpack_compose.ui.setting_budget.view_model

import android.util.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class BudgetViewModel : BaseViewModel() {
    val budgetState: MutableStateFlow<BudgetEntity> = MutableStateFlow(BudgetEntity())
    val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    var monthPosition: MutableStateFlow<Float> = MutableStateFlow(0f)
    var dayPosition: MutableStateFlow<Float> = MutableStateFlow(0f)
    val monthRange = 100000
    val dayRange = 10000

    init {
        loadBudget()
    }

    fun loadBudget() {
        viewModelScope.launch {
            budgetRepository.read("").onSuccess {
                budgetState.value = it!!

                monthPosition.value = (it.month / monthRange).toFloat()
                dayPosition.value = (it.day / dayRange).toFloat()
            }.onFailure {
            }
        }
    }

    fun updateMonthBudget(month: Long) {
        budgetState.update {
            it.copy(month = month * monthRange)
        }
    }

    fun updateDayBudget(day: Long) {
        budgetState.update {
            it.copy(day = day * dayRange)
        }
    }

    fun onSave() {
        viewModelScope.launch {
            budgetRepository.create(budgetState.value)
        }
    }

}

data class BudgetEntity(val month: Long = 0, val day: Long = 0)
