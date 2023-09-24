package com.example.android_jetpack_compose.entity

interface Category {
    abstract  val name: String
    abstract val id: Int
}

sealed class ExpenseCategory : Category  {
    data class Income(override val name: String, override val id: Int) : ExpenseCategory()
    data class Expense(override val name: String, override val id: Int) : ExpenseCategory()
}
