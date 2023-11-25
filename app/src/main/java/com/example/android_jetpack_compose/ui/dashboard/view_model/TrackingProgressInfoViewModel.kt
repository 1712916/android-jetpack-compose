package com.example.android_jetpack_compose.ui.dashboard.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class WeekTrackerInfoViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()
    private val dashBoardRepository: DashBoardRepository = DashBoardRepositoryImpl(Date())

    init {
        _weekTrackerInfoState.value = WeekTrackerInfoModel(
            weekTackers = WeekByDate(Date()).getWeekDates().map {
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
        val a = viewModelScope.launch {
            _weekTrackerInfoState.value =
                dashBoardRepository.getProgressData()
        }

        a.join()

        _isLoading.value = false

    }
}
