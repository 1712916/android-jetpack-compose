package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.DateExpense
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.ktx.*
import java.util.Calendar
import java.util.Date

abstract class GetExpenseRepository {
    abstract suspend fun getExpense(date: Date): DateExpense
}

class GetExpenseRepositoryImpl : GetExpenseRepository(), FirebaseUtil {
    override suspend fun getExpense(date: Date): DateExpense {
        val dailyExpenseRepository: DailyExpenseRepository = InputDailyExpenseRepositoryImpl(date)
        val rs = dailyExpenseRepository.getList()
        val expenseList = rs.getOrNull()
        val total: Long = expenseList?.fold(0) { r, t ->
            r!! + t.money
        } ?: 0
        return DateExpense(date, total)
    }
}
