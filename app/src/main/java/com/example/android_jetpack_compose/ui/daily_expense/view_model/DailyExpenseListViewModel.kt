package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.expense.*
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
class DailyExpenseListViewModelImpl(val date: Date) :
    DailyExpenseListViewModel(InputDailyExpenseRepositoryImpl(date)) {
}

abstract class DailyExpenseListViewModel(val repo: DailyExpenseRepository) :
    ViewModel() {
    var expenseList: LiveData<List<MoneyModel>>
    var totalMoney: LiveData<Long>

    init {
        expenseList = repo.getLiveDataList()

        totalMoney = expenseList.map {
            it.fold(0) { sum, e -> sum + e.money }
        }
    }

    fun add(expense: MoneyModel) {
        viewModelScope.launch {
            repo.create(expense).onSuccess { }.onFailure { }
        }
    }

    fun remove(expense: MoneyModel) {
        viewModelScope.launch {
            repo.delete(expense.id).onSuccess { }.onFailure { }
        }
    }
}
