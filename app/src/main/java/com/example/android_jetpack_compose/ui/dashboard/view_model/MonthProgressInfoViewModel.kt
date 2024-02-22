package com.example.android_jetpack_compose.ui.dashboard.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

data class MonthProgress(val budget: Long = 0, val totalSpend: Long = 0)
class MonthProgressInfoViewModel : ViewModel() {
    val monthTackerInfoState = MutableStateFlow(MonthProgress())
    val monthExpenseRepository: MonthExpenseRepository = MonthExpenseRepositoryImpl(LocalDate.now())

    init {
        viewModelScope.launch {
            loadData()
        }

    }

    suspend fun loadData() {
        val rs = monthExpenseRepository.getProgressData()
        monthTackerInfoState.emit(
            MonthProgress(
                rs.budget,
                rs.totalSpend,
            )
        )
    }
}
