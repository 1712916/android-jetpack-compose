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
    suspend fun getTotalExpense(expenses: List<DateExpense>): Long {
        return expenses.fold(0) { sum, element -> sum + element.money }
    }
}

class DashBoardRepositoryImpl : DashBoardRepository() {
    override suspend fun getWeekProgressData(date: Date): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getWeekExpenseByDate(date)
        //get list of period week
        val periodWeekExpenses = getWeekExpenseByDate(date)
        val totalSpend = getTotalExpense(expenses)
        val totalPeriodSpend = getTotalExpense(periodWeekExpenses)
        val dateBudget: Double = 1000000.0
        //        val dateBudget: Double = GetDayBudgetRepository().getBudget()
        return WeekTrackerInfoModel(
            totalSpend = totalSpend,
            differenceNumber = DifferentExpenseUtil(
                totalPeriodSpend,
                totalSpend
            ).differenceNumber(),
            differentEnum = DifferentExpenseUtil(totalPeriodSpend, totalSpend).differentEnum(),
            weekTackers = expenses.map { dateData ->
                WeekTrackerModel(
                    date = dateData.date,
                    dateSpend = dateData.money,
                    dateBudget = dateBudget,
                )
            }.toTypedArray(),
        )
    }

    override suspend fun getWeekExpenseByDate(date: Date): List<DateExpense> {
        //tính ra tuần hiện tại
        val days = WeekByDate(date).getWeekDates()
        val getExpenseRepository: GetExpenseRepository = GetExpenseRepositoryImpl()

        return days.map { day -> getExpenseRepository.getExpense(day) }
    }
}
