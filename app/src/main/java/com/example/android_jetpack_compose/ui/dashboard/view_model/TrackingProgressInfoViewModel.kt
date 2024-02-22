package com.example.android_jetpack_compose.ui.dashboard.view_model

import android.os.*
import androidx.annotation.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
class WeekTrackerInfoViewModel(date: LocalDate) : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()
    private val dashBoardRepository: DashBoardRepository = DashBoardRepositoryImpl(date)
    private val preWeekDashBoardRepository: DashBoardRepository =
        DashBoardRepositoryImpl(date)

    init {
        _weekTrackerInfoState.value = WeekTrackerInfoModel(
            weekTackers = GetWeekDate(date).getDates().map {
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

class WeekTrackerInfoViewModelViewModelFactory(
    private val date: LocalDate,
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeekTrackerInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeekTrackerInfoViewModel(date) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
