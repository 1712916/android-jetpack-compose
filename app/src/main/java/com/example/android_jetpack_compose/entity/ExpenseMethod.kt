package com.example.android_jetpack_compose.entity

//tiền mặt
//thẻ (Thẻ thì có tên thẻ)
//khác (Người khác trả)
sealed class ExpenseMethod : Category {
    data class Cash(override val name: String = "Tien mat", override val id: Int = 0): ExpenseMethod()
    data class Bank(override val name: String, override val id: Int = 1): ExpenseMethod()
    data class Credit(override val name: String, override val id: Int = 2): ExpenseMethod()
    data class Other(override val name: String, override val id: Int = 3): ExpenseMethod()
}
