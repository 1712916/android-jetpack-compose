package com.example.android_jetpack_compose.ui.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.android_jetpack_compose.data.dashboard.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class WeekTrackerInfoViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)

    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()
    private  val dashBoardRepository: DashBoardRepository =  DashBoardRepositoryImpl()
    init {
        loadData()
    }

    private fun loadData() {
        _weekTrackerInfoState.value = dashBoardRepository.getWeekProgressData(Calendar.getInstance().time)

        _isLoading.value = false
    }
}
