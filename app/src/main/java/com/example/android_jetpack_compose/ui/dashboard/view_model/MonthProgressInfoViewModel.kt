package com.example.android_jetpack_compose.ui.dashboard.view_model

import android.view.View
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.*
import java.time.format.*
import java.util.*

data class MonthProgress(val budget: Long = 0, val totalSpend: Long = 0)
class MonthProgressInfoViewModel : ViewModel() {
    val monthTackerInfoState = MutableStateFlow(MonthProgress())
    val monthExpenseRepository: MonthExpenseRepository = MonthExpenseRepositoryImpl(Date())

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
