package com.example.android_jetpack_compose.ui.daily_expense.view_model

import com.example.android_jetpack_compose.entity.*

data class InputDailyExpenseState(
    val money: String? = null, val note: String? = null,
    val method: ExpenseMethod? = null, val category: ExpenseCategory? = null,
)

class InputDailyExpenseStateValidator(private val inputDailyExpenseState: InputDailyExpenseState) {
    fun validate(): Boolean {
        return !inputDailyExpenseState.money.isNullOrEmpty() && inputDailyExpenseState.method != null && inputDailyExpenseState.category != null
    }
}
