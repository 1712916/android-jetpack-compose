package com.example.android_jetpack_compose.ui.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@Preview(backgroundColor = 0x00BBBBBE)
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(40.dp),
    ) {

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .clickable {
                    start = start.minus(DatePeriod(days = 7))
                    end = start.plus(DatePeriod(days = 6))
                    onChanged(start, end)
                }) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "pre week",
                modifier = Modifier.background(Color.White)

            )
        }

        val isCurrentWeek = start == currentStart && end == currentEnd
        val color = if (isCurrentWeek) MaterialTheme.colors.primary else MaterialTheme.typography.body1.color


        Text(text = formatDate(start), style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight(600), color = color))
        Text(" to ",style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight(600), color = color))
        Text(text = formatDate(end), style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight(600), color = color))

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clip(CircleShape)
                .clickable {
                    start = start.plus(DatePeriod(days = 7))
                    end = start.plus(DatePeriod(days = 6))
                    onChanged(start, end)
                })
        {
            Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "next week",
                modifier = Modifier.background(Color.White)
                )
        }
    }
}

private fun formatDate(date: LocalDate): String {
    return "${if (date.dayOfMonth >= 10) date.dayOfMonth else "0${date.dayOfMonth}"}-${if (date.monthNumber >= 10) date.monthNumber else "0${date.monthNumber}"}-${date.year%100}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMondayFromDate(date: LocalDate): LocalDate {
    var monday = date
    while (monday.dayOfWeek != DayOfWeek.MONDAY) {
        monday = monday.minus(DatePeriod(days = 1))
    }
    return monday
}
