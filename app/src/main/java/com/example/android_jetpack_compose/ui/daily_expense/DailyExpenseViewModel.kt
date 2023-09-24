package com.example.android_jetpack_compose.ui.daily_expense

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.android_jetpack_compose.data.dashboard.DashBoardRepository
import com.example.android_jetpack_compose.data.dashboard.DashBoardRepositoryImpl
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepository
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseViewModel(private val dailyExpenseRepository: DailyExpenseRepository) :
    ViewModel() {

    private val dailyExpenseState = MutableStateFlow(DailyExpenseModel())
    
    val dailyExpenseStateFlow: StateFlow<DailyExpenseModel> = dailyExpenseState.asStateFlow()

    fun init() {
        dailyExpenseState.value = DailyExpenseModel(expenses = dailyExpenseRepository.getExpensesByDate(Date()).toMutableList()).refreshData()
    }

    fun add(expense: MoneyModel) {
        dailyExpenseState.value = dailyExpenseState.value.add(expense)
    }

    fun remove(expense: MoneyModel) {
        dailyExpenseState.value = dailyExpenseState.value.remove(expense)
    }

    fun update(index: Int, expense: MoneyModel) {
        dailyExpenseState.value = dailyExpenseState.value.update(index, expense)
    }

    fun save() {
        dailyExpenseState.value.expenses.forEach() {
            moneyModel ->  dailyExpenseRepository.create(moneyModel)
        }
    }
}

data class DailyExpenseModel(
    val totalSpend: Long = 0,
    val expenses: MutableList<MoneyModel> = arrayListOf(),
) {
    fun add(expense: MoneyModel): DailyExpenseModel {
        expenses.plus(expense)

        return refreshData()
    }

    fun remove(expense: MoneyModel): DailyExpenseModel {
        expenses.minus(expense)

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
