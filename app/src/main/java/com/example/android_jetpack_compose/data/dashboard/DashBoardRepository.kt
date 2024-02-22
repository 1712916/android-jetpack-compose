package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.*
import kotlinx.datetime.*

abstract class DashBoardRepository : GetProgressData
class DashBoardRepositoryImpl(date: LocalDate) : DashBoardRepository() {
    private val weekExpenseRepository: WeekExpenseRepository = WeekExpenseRepositoryImpl(date)
    override suspend fun getProgressData(): DatesTrackerInfoModel {
        return weekExpenseRepository.getProgressData()
    }
}
