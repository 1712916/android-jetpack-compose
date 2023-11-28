package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import kotlinx.coroutines.*
import org.threeten.bp.*
import java.util.*

abstract class MonthExpenseRepository(val date: Date) : ProgressExpenseRepository
class MonthExpenseRepositoryImpl(date: Date) : MonthExpenseRepository(date), FirebaseUtil {
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    override suspend fun getDateExpenses(): List<DateExpense> {
        val days = mutableListOf<Date>()
        val calendar = Calendar.getInstance() // this takes current date

        calendar[Calendar.DAY_OF_MONTH] = 1
        val date = LocalDate.of(date.year, date.month, date.day)
        val lengthOfMonth = date.lengthOfMonth()

        while (days.count() < lengthOfMonth - 1) {
            days.add(calendar.time)
            calendar.add(Calendar.DATE, 1);
        }
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
            budget = budget?.month ?: 0,
        )
    }
}
