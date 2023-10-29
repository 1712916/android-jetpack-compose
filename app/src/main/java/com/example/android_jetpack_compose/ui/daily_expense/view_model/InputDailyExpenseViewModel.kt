package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

/*
* requires: money, category, method,
* optional: note
*
* Format number:
*  example: 001 -> 1
*  example: 1002 -> 1.002
*
* Suggest:
*  example: 15 -> 15.000, 150.000, 1.500.000
* */

class InputDailyExpenseViewModel : ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(
        InputDailyExpenseState(
            money = ""
        )
    )
    val uiState: StateFlow<InputDailyExpenseState> = _uiState.asStateFlow()
    fun changeMoney(moneyString: String) {
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

    fun onSave() {
        //validate
        ///check required fields
        ///
        _uiState.value.let {

            if (it.category == null || it.method == null || it.money.isEmpty()) {
                return
            }

            val model = MoneyModel(
                money = it.money.toLong(),
                expenseMethod = it.method,
                expenseCategory = it.category,
                note = it.note,
                createDate = Date(),
                updateDate = Date()
            )

            Log.i("onSave",model.toString())

        }




        ///create date
        ///update date
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
}

data class InputDailyExpenseState(val money: String, val note: String? = null,
    val method: ExpenseMethod? = null, val category: ExpenseCategory? = null
    )
