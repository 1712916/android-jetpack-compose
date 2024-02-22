package com.example.android_jetpack_compose.util.date

import java.text.*
import java.util.*

class CDate(val date: Date) {
    //now
    companion object {
        fun now(): Date {
            return Calendar.getInstance().time
        }

        fun parseDate(dateString: String, pattern: String = "yyyy-MM-dd"): Date? {
            try {
                val formatter = SimpleDateFormat(pattern)
                return formatter.parse(dateString)
            } catch (e: ParseException) {
                return null
            }
        }
    }

    fun day(): Int {
        val dateFormat = SimpleDateFormat("d")
        return dateFormat.format(date).toInt()
    }

    fun month(): Int {
        val dateFormat = SimpleDateFormat("M")
        return dateFormat.format(date).toInt() - 1
    }

    fun year(): Int {
        val dateFormat = SimpleDateFormat("yyyy")
        return dateFormat.format(date).toInt()
    }


}
