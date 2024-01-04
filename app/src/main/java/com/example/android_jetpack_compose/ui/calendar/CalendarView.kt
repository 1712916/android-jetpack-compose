package com.example.android_jetpack_compose.ui.calendar

import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import java.text.*
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CalendarView() {
    var date by remember { mutableStateOf(Calendar.getInstance().time) }
    val textTheme = MaterialTheme.typography

    Row {
        IconButton(onClick = {
            val c = Calendar.getInstance()
            c.apply {
                set(Calendar.YEAR, getYearFromDate(date))
                set(Calendar.MONTH, date.month)
                add(Calendar.MONTH, -1)
            }
            date = c.time
            Log.i("LOLLL", date.toString())
        }) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "<"
            )

        }
        Text(
            SimpleDateFormat("M-yyyy").format(date),
            style = textTheme.displaySmall
        )
        IconButton(onClick = {
            val c = Calendar.getInstance()
            c.apply {
                set(Calendar.YEAR, getYearFromDate(date))
                set(Calendar.MONTH, date.month)
                add(Calendar.MONTH, 1)
            }
            date = c.time
        }) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "<"
            )

        }
    }
    MonthView(
        date
    )
}

fun getYearFromDate(date: Date): Int {
    val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return dateFormat.format(date).toInt()
}

@Composable
private fun MonthView(date: Date) {

    var dates by remember { mutableStateOf(listOf<Date>()) }

    LaunchedEffect(date) {
        // Perform an asynchronous operation
        val result = withContext(Dispatchers.IO) {
            // This will be executed on a background thread
            GetMonthDate(date).getDates()
        }

        // Update the UI with the result
        dates = result
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        items(dates) {
            DateView(it)
        }
    }
}

@Composable
private fun DateView(date: Date) {
    var money by remember { mutableStateOf(0) }
    val textTheme = MaterialTheme.typography
    Column(
        modifier = Modifier.background(color = Color.White),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            SimpleDateFormat("d").format(date),
            style = textTheme.labelSmall.copy(
                color = Color.Gray
            )

        )
        Text(
            money.toString(),
            style = textTheme.headlineSmall
        )
    }
}
