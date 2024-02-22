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

fun Long.reduceMoneyFormat(): String {
    return when {
        this >= 1_000_000_000 -> "${
            String.format("%.1f", this / 1_000_000_000.0).replace(".0", "")
        }b"

        this >= 1_000_000 -> "${String.format("%.1f", this / 1_000_000.0).replace(".0", "")}m"
        this >= 1_000 -> "${String.format("%.1f", this / 1_000.0).replace(".0", "")}k"
        else -> "$this"
    }
}
