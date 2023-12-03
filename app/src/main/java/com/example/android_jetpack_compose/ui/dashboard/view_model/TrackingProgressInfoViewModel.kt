package com.example.android_jetpack_compose.ui.dashboard.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class WeekTrackerInfoViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()
    private val dashBoardRepository: DashBoardRepository = DashBoardRepositoryImpl(Date())
    private val preWeekDashBoardRepository: DashBoardRepository =
        DashBoardRepositoryImpl(Date(Date().time - 7 * 86400 * 1000))

    init {
        _weekTrackerInfoState.value = WeekTrackerInfoModel(
            weekTackers = GetWeekDate(Date()).getDates().map {
                WeekTrackerModel(
                    date = it,
                    dateSpend = 0,
                )
            }.toTypedArray(),
            budget = 200000,
        )

        viewModelScope.launch {
            loadData()
        }
    }

    suspend fun loadData() {
        val deferredResults: List<Deferred<DatesTrackerInfoModel>> = listOf(
            GlobalScope.async {
                dashBoardRepository.getProgressData()
            },
            GlobalScope.async {
                preWeekDashBoardRepository.getProgressData()
            }
        )
        val results = deferredResults.awaitAll()
        val currentWeekExpense = results.first()
        val previousWeekExpense = results.last()
        val differentExpenseUtil: DifferentExpenseUtil = DifferentExpenseUtil(
            previousWeekExpense.totalSpend,
            currentWeekExpense.totalSpend,
        )

        _weekTrackerInfoState.value = WeekTrackerInfoModel(
            weekTackers = currentWeekExpense.weekTackers,
            budget = currentWeekExpense.budget,
            totalSpend = currentWeekExpense.totalSpend,
            differentEnum = differentExpenseUtil.differentEnum(),
            differenceNumber = differentExpenseUtil.differenceNumber(),
        )


        _isLoading.value = false
    }
}
