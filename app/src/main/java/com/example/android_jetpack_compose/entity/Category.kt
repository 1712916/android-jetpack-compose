package com.example.android_jetpack_compose.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

interface Category {
    val name: String
    val id: Int
}


@Serializable
data class ExpenseCategory(override val name: String, override val id: Int) : Category
