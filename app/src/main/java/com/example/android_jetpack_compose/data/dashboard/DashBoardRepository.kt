package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.DateExpense
import com.example.android_jetpack_compose.entity.DifferentEnum
import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import com.example.android_jetpack_compose.entity.WeekTrackerModel
import com.example.android_jetpack_compose.util.*
import java.util.Calendar
import java.util.Date

abstract class DashBoardRepository {
    abstract suspend fun getWeekProgressData(date: Date): WeekTrackerInfoModel
    protected abstract suspend fun getWeekExpenseByDate(date: Date): List<DateExpense>
    fun getTotalExpense(expenses: List<DateExpense>): Long {
        return expenses.fold(0) { sum, element -> sum + element.money }
    }
}

class DashBoardRepositoryImpl : DashBoardRepository() {
    override suspend fun getWeekProgressData(date: Date): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getWeekExpenseByDate(date)
        //get list of period week
        val periodWeekExpenses = getWeekExpenseByDate(Date(date.time - 7 * 86400 * 1000))
        val totalSpend = getTotalExpense(expenses)
        val totalPeriodSpend = getTotalExpense(periodWeekExpenses)
        val differentExpenseUtil: DifferentExpenseUtil = DifferentExpenseUtil(
            totalPeriodSpend,
            totalSpend
        )

        return WeekTrackerInfoModel(
            totalSpend = totalSpend,
            differenceNumber = differentExpenseUtil.differenceNumber(),
            differentEnum = differentExpenseUtil.differentEnum(),
            weekTackers = expenses.map { dateData ->
                WeekTrackerModel(
                    date = dateData.date,
                    dateSpend = dateData.money,
                )
            }.toTypedArray(),
            dayBudget = 200000,
        )
    }

    override suspend fun getWeekExpenseByDate(date: Date): List<DateExpense> {
        //tính ra tuần hiện tại
        val days = WeekByDate(date).getWeekDates()
        val getExpenseRepository: GetExpenseRepository = GetExpenseRepositoryImpl()

        return days.map { day -> getExpenseRepository.getExpense(day) }
    }
}
