package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import java.util.*

abstract class WeekExpenseRepository(val date: Date) : GetDateExpenses, GetTotalExpense,
    GetProgressData {
}

class WeekExpenseRepositoryImpl(date: Date) : WeekExpenseRepository(date) {
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    override suspend fun getDateExpenses(): List<DateExpense> {
        val days = WeekByDate(date).getWeekDates()
        val getExpenseRepository: GetExpenseRepository = GetExpenseRepositoryImpl()

        return days.map { day -> getExpenseRepository.getExpense(day) }
    }

    override suspend fun getTotalExpense(): Long {
        return getDateExpenses().fold(0) { sum, element -> sum + element.money }
    }

    override suspend fun getProgressData(): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getDateExpenses()
        val totalSpend = getTotalExpense()
        //get list of period week
        val previousWeekExpenseRepository: WeekExpenseRepository =
            WeekExpenseRepositoryImpl(Date(date.time - 7 * 86400 * 1000))
        val totalPeriodSpend = previousWeekExpenseRepository.getTotalExpense()
        val differentExpenseUtil: DifferentExpenseUtil = DifferentExpenseUtil(
            totalPeriodSpend,
            totalSpend
        )
        val budget = budgetRepository.read("").getOrNull()


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
            dayBudget = budget?.day ?: 0,
        )
    }
}
