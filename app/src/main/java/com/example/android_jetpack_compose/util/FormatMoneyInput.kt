package com.example.android_jetpack_compose.util

import java.text.*
import java.util.*

private val moneyFormat: NumberFormat = DecimalFormat("#,###").apply {
    maximumFractionDigits = 0
    currency = Currency.getInstance("EUR")
}

class FormatMoneyInput(private val number: String) {
    override fun toString(): String {
        return try {
            moneyFormat.format(number.toLong()).replace(",", ".")
        } catch (e: Exception) {
            number
        }
    }
}

fun Long.money(): String {
    return moneyFormat.format(this).replace(",", ".")
}
