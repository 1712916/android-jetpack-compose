package com.example.android_jetpack_compose.entity

import java.util.*

data class MoneyModel(
    val id: String = "",
    val money: Long = 0,
    val note: String? = null,
    val category: ExpenseCategory? = null,
    val method: ExpenseMethod? = null,
    val createDate: Date? = null,
    val updateDate: Date? = null,
)
