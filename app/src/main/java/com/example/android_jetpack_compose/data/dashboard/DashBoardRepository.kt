package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.DateExpense
import com.example.android_jetpack_compose.entity.DifferentEnum
import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import com.example.android_jetpack_compose.entity.WeekTrackerModel
import com.example.android_jetpack_compose.util.DifferentExpenseUtil
import java.util.Calendar
import java.util.Date

abstract class DashBoardRepository {
    abstract fun getWeekProgressData(date: Date): WeekTrackerInfoModel
    protected abstract fun getWeekExpenseByDate(date: Date): List<DateExpense>
    fun getTotalExpense(expenses: List<DateExpense>): Double {
        return expenses.fold(0.0) { sum, element -> sum + element.money }
    }
}

class DashBoardRepositoryImpl : DashBoardRepository() {
    override fun getWeekProgressData(date: Date): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getWeekExpenseByDate(Calendar.getInstance().time)
        //get list of period week
        val periodWeekExpenses = getWeekExpenseByDate(Calendar.getInstance().time)
        val totalSpend = getTotalExpense(expenses)
        val totalPeriodSpend = getTotalExpense(periodWeekExpenses)
        val dateBudget: Double = GetDayBudgetRepository().getBudget()

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

    override fun getWeekExpenseByDate(date: Date): List<DateExpense> {
        //tính ra tuần hiện tại
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        val days = mutableListOf<Date>()

        for (i in 0..6) {
            days.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        val getExpenseRepository: GetExpenseRepository = GetExpenseRepositoryImpl()

        return days.map { day -> getExpenseRepository.getExpense(day) }
    }
}
