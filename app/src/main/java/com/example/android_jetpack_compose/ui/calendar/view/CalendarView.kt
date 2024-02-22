package com.example.android_jetpack_compose.ui.calendar.view

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
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.resouce.*
import com.example.android_jetpack_compose.ui.calendar.view_model.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.date.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView(viewModel: CalendarViewModel) {

    val textTheme = MaterialTheme.typography
    val dateData by viewModel.dateExpenseMutableStateFlow.collectAsState()

    val date by viewModel.dateStateFlow.collectAsState()


    Column {
        Row {
            IconButton(onClick = {
                viewModel.onPrevious()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "<"
                )
            }
            Text(
                date.format(DateFormatResource.monthAndYearFormat),
                style = textTheme.displaySmall
            )
            IconButton(onClick = {
                viewModel.onNext()
            }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = ">"
                )
            }
        }
        MonthView(
            dateData
        )
    }
}

@Composable
private fun MonthView(dateExpenseList: List<DateExpense>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(dateExpenseList) { date ->
            DateView(date)
        }
    }
}

//@Composable
//private fun MonthView(month: Int, year: Int, builder: @Composable (date: Date) -> Unit) {
//    val dates = GetMonthDate(CDate.parseDate("1/$month/$year", "dd/MM/yyyy")!!).getDates()
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(7),
//        horizontalArrangement = Arrangement.spacedBy(1.dp),
//        verticalArrangement = Arrangement.spacedBy(1.dp)
//    ) {
//        items(dates) { date ->
//            builder(date)
//        }
//    }
//}

@Composable
private fun DateView(dateExpense: DateExpense) {
    val textTheme = MaterialTheme.typography

    Column(
        modifier = Modifier.background(color = Color.White),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            dateExpense.date.format("d"),
            style = textTheme.labelSmall.copy(
                color = Color.Gray
            )

        )
        Text(
            dateExpense.money.reduceMoneyFormat(),
            style = textTheme.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Visible,
        )
    }
}

@Composable
fun AutosizeText(text: String) {

    var multiplier by remember { mutableStateOf(1f) }

    Text(
        text,
        maxLines = 1, // modify to fit your need
        overflow = TextOverflow.Visible,
        style = LocalTextStyle.current.copy(
            fontSize = LocalTextStyle.current.fontSize * multiplier
        ),
        onTextLayout = {
            if (it.hasVisualOverflow) {
                multiplier *= 0.99f // you can tune this constant
            }
        }
    )
}
