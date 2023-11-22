package com.example.android_jetpack_compose.entity

import kotlinx.serialization.Serializable

//tiền mặt
//thẻ (Thẻ thì có tên thẻ)
//khác (Người khác trả)
@Serializable
data class ExpenseMethod(override val name: String = "", override val id: Int = -1) : Category
