package com.example.android_jetpack_compose.ui.daily_expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

abstract class DailyExpenseViewModel(val repository: DailyExpenseRepository) : BaseViewModel() {
    val _uiState = MutableStateFlow(InputDailyExpenseState())
    val uiState: StateFlow<InputDailyExpenseState> = _uiState.asStateFlow()
    val validateState: StateFlow<Boolean> = _uiState.mapState {
        InputDailyExpenseStateValidator(it).validate()
    }
    private val maximumMoneyNumber = 13
    fun changeMoney(moneyString: String) {
        if (moneyString.length > maximumMoneyNumber) {
            return
        }

        _uiState.update { currentState ->
            currentState.copy(
                money = moneyString
            )
        }
    }

    fun changeNote(note: String) {
        _uiState.update { currentState ->
            currentState.copy(
                note = note
            )
        }
    }

    fun changeMethod(method: ExpenseMethod) {
        _uiState.update { currentState ->
            currentState.copy(
                method = method
            )
        }
    }

    fun changeCategory(category: ExpenseCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }

    abstract fun onSave(saveAnBack: Boolean = true)
}
