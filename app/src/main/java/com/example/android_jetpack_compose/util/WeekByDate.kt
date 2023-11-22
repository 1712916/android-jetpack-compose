package com.example.android_jetpack_compose.util

import java.text.*
import java.util.*

class WeekByDate(val date: Date) {
    fun getWeekDates(): List<Date> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        val days = mutableListOf<Date>()
        for (i in 0..6) {
            days.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return days
    }

    fun dateOfWeek(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar[Calendar.DAY_OF_WEEK];
    }
}

fun main() {
    print(WeekByDate(date = Date()).dateOfWeek())
}
