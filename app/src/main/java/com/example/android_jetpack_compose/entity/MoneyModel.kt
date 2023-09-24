package com.example.android_jetpack_compose.entity

import java.util.Date

data class MoneyModel(
    val id: Long? = null,
    val money: Long,
    val note: String?,
    val expenseCategory: ExpenseCategory,
    val expenseMethod: ExpenseMethod,
    val createDate: Date,
    val updateDate: Date,
)
