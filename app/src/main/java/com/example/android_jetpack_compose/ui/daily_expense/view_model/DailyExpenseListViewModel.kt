package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.coroutines.*
import kotlinx.datetime.*

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseListViewModelImpl(val date: LocalDate) :
    DailyExpenseListViewModel(InputDailyExpenseRepositoryImpl(date)) {

    init {
        val settingDailyExpenseRepository: DailyExpenseRepository =
            SettingDefaultExpenseRepositoryImpl()
        viewModelScope.launch {
            repository.getList().onSuccess {
                if (it.isEmpty()) {
                    val defaultExpenseListResult = settingDailyExpenseRepository.getList()

                    defaultExpenseListResult.onSuccess { expenses ->
                        expenses.forEach { expense ->
                            Log.i("defaultExpense", expense.money.toString())
                            add(expense)
                        }
                    }
                }
            }.onFailure {
            }
        }
    }
}

abstract class DailyExpenseListViewModel(val repository: DailyExpenseRepository) :
    ViewModel() {
    var expenseList: LiveData<List<MoneyModel>>
    var totalMoney: LiveData<Long>
    val deleteMode: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        expenseList = repository.getLiveDataList()

        totalMoney = expenseList.map {
            it.fold(0) { sum, e -> sum + e.money }
        }
    }

    fun add(expense: MoneyModel) {
        viewModelScope.launch {
            repository.create(expense).onSuccess { }.onFailure { }
        }
    }

    fun remove(expense: MoneyModel) {
        viewModelScope.launch {
            repository.delete(expense.id).onSuccess { }.onFailure { }
        }
    }
}
