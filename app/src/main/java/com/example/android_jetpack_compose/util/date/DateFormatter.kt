package com.example.android_jetpack_compose.util.date

import kotlinx.datetime.*
import java.text.*
import java.util.*

fun Date.format(pattern: String = "dd-MM-yyyy"): String {
    return SimpleDateFormat(pattern).format(this)
}

fun LocalDate.formatMonth(): String {
    return "${formatDateNumber(this.monthNumber)}-${this.year}"
}

fun LocalDate.formatDay(): String {
    return "${formatDateNumber(this.dayOfMonth)}-${formatDateNumber(this.monthNumber)}-${this.year}"
}

fun LocalDate.formatDayParam(): String {
    return "${this.year}-${formatDateNumber(this.monthNumber)}-${formatDateNumber(this.dayOfMonth)}"
}

private fun formatDateNumber(number: Int): String {
    return if (number < 10) "0$number" else number.toString()
}
