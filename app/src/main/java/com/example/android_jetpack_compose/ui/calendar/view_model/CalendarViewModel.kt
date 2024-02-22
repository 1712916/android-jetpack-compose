package com.example.android_jetpack_compose.ui.calendar.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

class CalendarViewModel : BaseViewModel() {
    var _dateStateFlow = MutableStateFlow(LocalDate.now())
    var dateStateFlow = _dateStateFlow.asStateFlow()

    private var _dateExpenseMutableStateFlow = MutableStateFlow<List<DateExpense>>(listOf())
    var dateExpenseMutableStateFlow = _dateExpenseMutableStateFlow.asStateFlow()

    init {
        loadData(dateStateFlow.value)

        viewModelScope.launch {
            _dateStateFlow.collect {
                Log.i("Date change", it.toString())
                loadData(dateStateFlow.value)
            }
        }

    }

    fun loadData(date: LocalDate) {
        val monthExpenseRepository: MonthExpenseRepository = MonthExpenseRepositoryImpl(date)
        viewModelScope.launch {
            _dateExpenseMutableStateFlow.value = monthExpenseRepository.getDateExpenses()
        }
    }

    fun onNext() {
        _dateStateFlow.value = nextMonth(_dateStateFlow.value)
    }

    fun onPrevious() {
        _dateStateFlow.value = previousMonth(_dateStateFlow.value)
    }
}

fun nextMonth(date: LocalDate): LocalDate {
    return date.plus(DatePeriod(months = 1))
}

fun previousMonth(date: LocalDate): LocalDate {
    return date.minus(DatePeriod(months = 1))
}
