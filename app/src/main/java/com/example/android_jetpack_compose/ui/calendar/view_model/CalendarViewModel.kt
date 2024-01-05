package com.example.android_jetpack_compose.ui.calendar.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.date.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class CalendarViewModel : BaseViewModel() {
    var _dateStateFlow = MutableStateFlow(CDate.now())
    var dateStateFlow = _dateStateFlow.asStateFlow()

    private var _dateExpenseMutableStateFlow = MutableStateFlow<List<DateExpense>>(listOf())
    var dateExpenseMutableStateFlow = _dateExpenseMutableStateFlow.asStateFlow()

    init {
        loadData(dateStateFlow.value)

        viewModelScope.launch {
            _dateStateFlow.collect {
                Log.i("Date change", it.format())
                loadData(dateStateFlow.value)
            }
        }

    }

    fun loadData(date: Date) {
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

fun nextMonth(date: Date): Date {
    val calendar = Calendar.getInstance()
    val cDate = CDate(date)
    calendar.apply {
        set(Calendar.YEAR, cDate.year())
        set(Calendar.MONTH, cDate.month())
        set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, 1)
    }

    return calendar.time
}

fun previousMonth(date: Date): Date {
    val calendar = Calendar.getInstance()
    val cDate = CDate(date)
    calendar.apply {
        set(Calendar.YEAR, cDate.year())
        set(Calendar.MONTH, cDate.month())
        set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, -1)
    }

    return calendar.time
}
