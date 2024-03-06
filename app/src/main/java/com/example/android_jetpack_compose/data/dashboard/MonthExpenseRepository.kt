package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.datetime.*

abstract class MonthExpenseRepository(val date: LocalDate) : ProgressExpenseRepository
class MonthExpenseRepositoryImpl(date: LocalDate) : MonthExpenseRepository(date), FirebaseUtil {
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    override suspend fun getDateExpenses(): List<DateExpense> {
        val days = GetMonthDate(date).getDates()
        val getDateExpenseRepository: GetDateExpenseRepository = GetDateExpenseRepositoryImpl()
        val deferredResults: List<Deferred<DateExpense>> = days.map { day ->
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
        //list of item.name())expense each date
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
            budget = budget?.month ?: 0,
        )
    }
}
