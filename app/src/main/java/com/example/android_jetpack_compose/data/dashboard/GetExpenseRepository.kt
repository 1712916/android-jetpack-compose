package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.DateExpense
import java.util.Calendar
import java.util.Date

abstract class GetExpenseRepository {
    abstract fun getExpense(date: Date): DateExpense
}

class GetExpenseRepositoryImpl : GetExpenseRepository() {
    override fun getExpense(date: Date): DateExpense {
        //todo: need to fix
        return DateExpense(Calendar.getInstance().time, 200)
    }
}
