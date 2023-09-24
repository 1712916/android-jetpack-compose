package com.example.android_jetpack_compose.util

import com.example.android_jetpack_compose.entity.DifferentEnum

class DifferentExpenseUtil(val preExpense: Double, val curExpense: Double) {
    fun differenceNumber(): Double {
        if (curExpense > 0) {
            return preExpense / curExpense
        }
        return 1.0
    }

    fun differentEnum(): DifferentEnum {
        val differenceNumber = differenceNumber()
        return if (differenceNumber.equals(1.0)) {
            DifferentEnum.BALANCE
        } else if (differenceNumber > 1.0) {
            DifferentEnum.INCREASE

        } else {
            DifferentEnum.DECREASE
        }
    }
}
