package com.example.android_jetpack_compose.ui.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun WeekPaginationPreview() {
    WeekPaginationView(onChanged = { start, end ->
        print("start: ${start}, end: ${end}")
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekPaginationView(
    initDate: LocalDate? = null,
    onChanged: (start: LocalDate, end: LocalDate) -> Unit,
) {

    val now = LocalDate.now()
    val currentStart = getMondayFromDate(now)
    val currentEnd = currentStart.plus(DatePeriod(days = 6))

    var start by remember { mutableStateOf(currentStart) }
    var end by remember { mutableStateOf(currentEnd) }

    if (initDate != null) {
        start = getMondayFromDate(initDate)
        end = start.plus(DatePeriod(days = 6))
    }


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            start = start.minus(DatePeriod(days = 7))
            end = start.plus(DatePeriod(days = 6))
            onChanged(start, end)
        }) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "pre week")
        }
        //if current week
        if (start == currentStart && end == currentEnd)
            Text(text = "Current Week")
        else {
            Text(text = formatDate(start))
            Text(" to ")
            Text(text = formatDate(end))
        }

        IconButton(onClick = {
            start = start.plus(DatePeriod(days = 7))
            end = start.plus(DatePeriod(days = 6))
            onChanged(start, end)
        }) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "next week")
        }
    }
}

private fun formatDate(date: LocalDate): String {
    return "${date.dayOfMonth}-${date.monthNumber}-${date.year}"
}

@RequiresApi(Build.VERSION_CODES.O)
  fun getMondayFromDate(date: LocalDate): LocalDate {
    var monday = date
    while (monday.dayOfWeek != DayOfWeek.MONDAY) {
        monday = monday.minus(DatePeriod(days = 1))
    }
    return monday
}
