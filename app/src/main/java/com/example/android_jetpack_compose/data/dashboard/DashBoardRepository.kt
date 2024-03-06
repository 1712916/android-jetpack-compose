package com.example.android_jetpack_compose.data.dashboard

import android.os.*
import androidx.annotation.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.datetime.*

abstract class DashBoardRepository : GetProgressData
class DashBoardRepositoryImpl(date: LocalDate) : DashBoardRepository() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val weekExpenseRepository: WeekExpenseRepository = WeekExpenseRepositoryImpl(date)
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getProgressData(): DatesTrackerInfoModel {
        return weekExpenseRepository.getProgressData()
    }
}
