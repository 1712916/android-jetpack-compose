package com.example.android_jetpack_compose.util

import android.os.*
import androidx.annotation.*
import com.example.android_jetpack_compose.ui.view.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.datetime.*
import java.util.*

abstract class GetListDate(val date: LocalDate) {
    abstract fun getDates(): List<LocalDate>

    //    fun dateOfWeek(): Int {
    //        val calendar = Calendar.getInstance()
    //        calendar.time = date
    //        return calendar[Calendar.DAY_OF_WEEK];
    //    }
}

class GetWeekDate(date: LocalDate) : GetListDate(date) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDates(): List<LocalDate> {
        var monday = getMondayFromDate(date)

        val days = mutableListOf<LocalDate>()

        for (i in 0..6) {
            days.add(monday.plus(DatePeriod(days = i)))
        }

        return days
    }
}

class GetMonthDate(date: LocalDate) : GetListDate(date) {
    override fun getDates(): List<LocalDate> {

        val days = mutableListOf<LocalDate>()

        val firstDay = getFirstDayOfMonth(date = date)

        for (i in 0..<numberDays(date.monthNumber, isLeapYear(date.year))) {
            days.add(firstDay.plus(DatePeriod(days = i)))
        }

        return days
    }

    fun getAllDatesOfMonth(year: Int, month: Int): List<LocalDate> {
        val datesList = mutableListOf<LocalDate>()

        // Create a calendar instance and set it to the first day of the month
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        // Get the maximum day of the month
        val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Iterate through the days of the month and format each date
        //        for (dayOfMonth in 1..lastDay) {
        //            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        //            datesList.add(calendar.time)
        //        }

        return datesList
    }
}

fun numberDays(month: Int, leapYear: Boolean): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        2 -> if (leapYear) 29 else 28
        else -> 30
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun getFirstDayOfMonth(date: LocalDate): LocalDate {
    return LocalDate(dayOfMonth = 1, month = date.month, year = date.year)
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    println(isLeapYear(2024))
    println(getFirstDayOfMonth(LocalDate.now()))
    println(LocalDate.now().monthNumber)
    println(LocalDate.now().monthNumber)
    println(GetWeekDate(LocalDate.now()).getDates())
    println(GetMonthDate(LocalDate.now()).getDates())
}
