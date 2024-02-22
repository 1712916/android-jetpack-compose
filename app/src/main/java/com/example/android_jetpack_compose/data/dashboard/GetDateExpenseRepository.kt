package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import kotlinx.datetime.*

abstract class GetDateExpenseRepository {
    abstract suspend fun getExpense(date: LocalDate): DateExpense
}

class GetDateExpenseRepositoryImpl : GetDateExpenseRepository(), FirebaseUtil {
    override suspend fun getExpense(date: LocalDate): DateExpense {
        val dailyExpenseRepository: DailyExpenseRepository = InputDailyExpenseRepositoryImpl(date)
        val rs = dailyExpenseRepository.getList()
        val expenseList = rs.getOrNull()
        val total: Long = expenseList?.fold(0) { r, t ->
            r!! + t.money
        } ?: 0
        return DateExpense(date, total)
    }
}
