package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import java.util.Date

abstract class DashBoardRepository : GetProgressData
class DashBoardRepositoryImpl(date: Date) : DashBoardRepository() {
    private val weekExpenseRepository: WeekExpenseRepository = WeekExpenseRepositoryImpl(date)
    override suspend fun getProgressData(): WeekTrackerInfoModel {
        return weekExpenseRepository.getProgressData()
    }
}
