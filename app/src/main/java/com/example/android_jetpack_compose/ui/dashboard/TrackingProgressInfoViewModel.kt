package com.example.android_jetpack_compose.ui.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

class TrackingProgressInfoViewModel : ViewModel() {
//    private val _
}

class WeekTrackerInfoViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(true)
    private val _weekTrackerInfoState = MutableStateFlow(WeekTrackerInfoModel())
    val weekTrackerInfoState: StateFlow<WeekTrackerInfoModel> = _weekTrackerInfoState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _weekTrackerInfoState.value = WeekTrackerInfoModel(
            differenceNumber = 13.0,
            differentEnum = DifferentEnum.Increase,
            totalSpend = 689.50,
            weekTackers = arrayOf(
                WeekTrackerModel(
                    date = Calendar.getInstance().time,
                    dateBudget = 100.0,
                    dateSpend = 50.0
                ),
                WeekTrackerModel(
                    date = Calendar.getInstance().time,
                    dateBudget = 100.0,
                    dateSpend = 50.0
                ),
                WeekTrackerModel(
                    date = Calendar.getInstance().time,
                    dateBudget = 100.0,
                    dateSpend = 50.0
                ),
                WeekTrackerModel(
                    date = Calendar.getInstance().time,
                    dateBudget = 100.0,
                    dateSpend = 50.0
                ),
                WeekTrackerModel(
                    date = Calendar.getInstance().time,
                    dateBudget = 100.0,
                    dateSpend = 50.0
                ),
            ),
        )

        _isLoading.value = false
    }
}
