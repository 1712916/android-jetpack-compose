package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepository
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepositoryImpl
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.*
import kotlinx.coroutines.*
import java.util.Date

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseViewModel(val date: Date) :
    ViewModel() {
    private val dailyExpenseRepository: DailyExpenseRepository = DailyExpenseRepositoryImpl(date)
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    var expenseList: LiveData<List<MoneyModel>>
    var totalMoney: LiveData<Long>

    init {
        expenseList = dailyExpenseRepository.getLiveDataList()

        totalMoney = expenseList.map {
            Log.i("LOL expenseList", it.toString())

            it.fold(0) { sum, e -> sum + e.money }
        }
    }

    fun add(expense: MoneyModel) {
        viewModelScope.launch {
            dailyExpenseRepository.create(expense).onSuccess { }.onFailure { }
        }
    }

    fun remove(expense: MoneyModel) {
        viewModelScope.launch {
            dailyExpenseRepository.delete(expense.id).onSuccess { }.onFailure { }
        }
    }
}
