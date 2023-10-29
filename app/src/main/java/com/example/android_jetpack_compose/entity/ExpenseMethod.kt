package com.example.android_jetpack_compose.entity

//tiền mặt
//thẻ (Thẻ thì có tên thẻ)
//khác (Người khác trả)
data class ExpenseMethod(override val name: String, override val id: Int) : Category
