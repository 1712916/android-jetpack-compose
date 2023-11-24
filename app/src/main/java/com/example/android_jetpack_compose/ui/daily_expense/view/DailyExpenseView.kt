package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.flow.*
import java.text.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyExpenseView(
    navController: NavController,
    date: Date,
    viewModel: DailyExpenseListViewModel
) {
    val expenseListState = viewModel.expenseList.observeAsState()
    val totalExpenseState = viewModel.totalMoney.observeAsState()
    val now = Date()
    val title = SimpleDateFormat("dd-MM-yyyy").format(date)
    val deleteMode = viewModel.deleteMode.observeAsState()

    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = if (title == SimpleDateFormat("dd-MM-yyyy").format(now)) "Today" else title,
                showBackButton = true,
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteMode.value = !viewModel.deleteMode.value!!
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete expense"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(
                    InputDailyExpense.route.replace(
                        oldValue = "{date}",
                        newValue = date.time.toString(),
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add expense"
                )
            }
        }
    ) { p ->
        Column(modifier = Modifier.padding(p)) {
            TotalExpenseView(totalExpenseState.value ?: 0)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                content = {
                    items(expenseListState.value?.count() ?: 0) { index ->
                        val it = expenseListState.value!![index]
                        ExpenseCard(
                            it,
                            onClick = {
                                navController.navigate(
                                    UpdateDailyExpense.route.replace(
                                        oldValue = "{id}",
                                        newValue = it.id,
                                    ).replace(
                                        oldValue = "{date}",
                                        newValue = date.time.toString(),
                                    )
                                )
                            },
                            showDelete = deleteMode.value!!,
                            onDelete = {
                                viewModel.remove(it)
                            }
                        )
                        Divider()
                    }
                },
            )
        }
    }
}
@Composable
fun TotalExpenseView(totalExpense: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .drawBehind {
                val strokeWidth = 10f
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${totalExpense.money()} VND",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
    }

}
