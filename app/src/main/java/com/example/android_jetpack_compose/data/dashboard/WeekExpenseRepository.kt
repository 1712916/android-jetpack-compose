package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import java.util.*

abstract class WeekExpenseRepository(val date: Date) : ProgressExpenseRepository
class WeekExpenseRepositoryImpl(date: Date) : WeekExpenseRepository(date) {
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    override suspend fun getDateExpenses(): List<DateExpense> {
        val days = GetWeekDate(date).getDates()
        val getDateExpenseRepository: GetDateExpenseRepository = GetDateExpenseRepositoryImpl()
        val deferredResults: List<Deferred<DateExpense>> = days.map { day ->
            // Use async to execute each function concurrently
            GlobalScope.async {
                getDateExpenseRepository.getExpense(day)
            }
        }

        return deferredResults.awaitAll()
    }

    override suspend fun getTotalExpense(): Long {
        return getDateExpenses().fold(0) { sum, element -> sum + element.money }
    }

    override suspend fun getProgressData(): DatesTrackerInfoModel {
        //list of total expense each date
        val expenses = getDateExpenses()
        val totalSpend = getTotalExpense()
        val budget = budgetRepository.read("").getOrNull()


        return DatesTrackerInfoModel(
            totalSpend = totalSpend,
            weekTackers = expenses.map { dateData ->
                WeekTrackerModel(
                    date = dateData.date,
                    dateSpend = dateData.money,
                )
            }.toTypedArray(),
            budget = budget?.day ?: 0,
        )
    }
}
