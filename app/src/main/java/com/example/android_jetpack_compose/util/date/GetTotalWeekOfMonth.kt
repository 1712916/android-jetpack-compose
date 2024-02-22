package com.example.android_jetpack_compose.util.date

import android.os.*
import androidx.annotation.*
import java.time.*
import java.time.temporal.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalWeeksInMonth(year: Int, month: Int): Int {
    val yearMonth = YearMonth.of(year, month)
    val weekFields = WeekFields.of(Locale.getDefault())
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val firstWeekNumber = firstDayOfMonth.get(weekFields.weekOfMonth())
    val lastWeekNumber = lastDayOfMonth.get(weekFields.weekOfMonth())
    return if (firstWeekNumber == 1 && lastWeekNumber == 1) 1 else lastWeekNumber
}
