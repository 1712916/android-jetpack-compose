package com.example.android_jetpack_compose.ui.daily_expense.view_model

import androidx.lifecycle.ViewModel
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepository
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepositoryImpl
import com.example.android_jetpack_compose.entity.MoneyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseViewModel() :
    ViewModel() {
    private val dailyExpenseRepository: DailyExpenseRepository = DailyExpenseRepositoryImpl()

    private val _uiState = MutableStateFlow(DailyExpenseModel())
    val uiState: StateFlow<DailyExpenseModel> = _uiState.asStateFlow()


    val dailyExpenseStateFlow: StateFlow<DailyExpenseModel> = _uiState.asStateFlow()

    fun init() {
        _uiState.value = DailyExpenseModel(expenses = dailyExpenseRepository.getExpensesByDate(Date()).toMutableList()).refreshData()
    }

    fun add(expense: MoneyModel) {
        _uiState.value = _uiState.value.add(expense)
    }

    fun remove(expense: MoneyModel) {
        _uiState.value = _uiState.value.remove(expense)
    }

    fun update(index: Int, expense: MoneyModel) {
        _uiState.value = _uiState.value.update(index, expense)
    }

    fun save() {
        _uiState.value.expenses.forEach() {
            moneyModel ->  dailyExpenseRepository.create(moneyModel)
        }
    }
}

data class DailyExpenseModel(
    val totalSpend: Long = 0,
    val expenses: MutableList<MoneyModel> = arrayListOf(),
) {
    fun add(expense: MoneyModel): DailyExpenseModel {
        expenses.add(expense)

        return refreshData()
    }

    fun remove(expense: MoneyModel): DailyExpenseModel {
        expenses.remove(expense)

        return refreshData()
    }

    fun update(index: Int, expense: MoneyModel): DailyExpenseModel {
        expenses[index] = expense

        return refreshData()
    }

    fun refreshData(): DailyExpenseModel {
        
        return DailyExpenseModel(
            totalSpend = expenses.fold(0) { sum, e -> sum + e.money },
            expenses = expenses
        )
    }
}
