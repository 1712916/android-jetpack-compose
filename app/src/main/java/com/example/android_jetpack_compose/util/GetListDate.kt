package com.example.android_jetpack_compose.util

import org.threeten.bp.*
import java.text.*
import java.util.*

abstract class GetListDate(val date: Date) {
    abstract fun getDates(): List<Date>

    //    fun dateOfWeek(): Int {
    //        val calendar = Calendar.getInstance()
    //        calendar.time = date
    //        return calendar[Calendar.DAY_OF_WEEK];
    //    }
}

class GetWeekDate(date: Date) : GetListDate(date) {
    override fun getDates(): List<Date> {
        val calendar = Calendar.getInstance().apply {
            time = date
            firstDayOfWeek = Calendar.MONDAY
        }

        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        val days = mutableListOf<Date>()

        for (i in 0..6) {
            days.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
    }
}

class GetMonthDate(date: Date) : GetListDate(date) {
    override fun getDates(): List<Date> {
        val days = mutableListOf<Date>()
        val calendar = Calendar.getInstance() // this takes current date

        calendar[Calendar.DAY_OF_MONTH] = 1

        var dateFormat = SimpleDateFormat("yyyy-MM-dd").format(date)
        var date = LocalDate.parse(dateFormat)
        //        val date = LocalDate.of(date.year, Month.of(date.month), date.day)
        val lengthOfMonth = date.lengthOfMonth()

        while (days.count() < lengthOfMonth - 1) {
            days.add(calendar.time)
            calendar.add(Calendar.DATE, 1);
        }
        return days
    }
}
