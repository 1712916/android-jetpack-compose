package com.example.android_jetpack_compose.ui.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class WeekTrackerInfoViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()
    private val dashBoardRepository: DashBoardRepository = DashBoardRepositoryImpl()

    init {
       viewModelScope.launch {
           loadData()
       }
    }

    private suspend fun loadData() {
        val a = viewModelScope.launch {
            _weekTrackerInfoState.value =
                dashBoardRepository.getWeekProgressData(Calendar.getInstance().time)
        }

        a.join()

        _isLoading.value = false

    }
}
