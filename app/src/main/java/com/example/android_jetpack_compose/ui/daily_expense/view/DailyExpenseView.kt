package com.example.android_jetpack_compose.ui.daily_expense.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.firebase_util.GetExpenseMethod
import com.example.android_jetpack_compose.ui.daily_expense.view_model.DailyExpenseViewModel
import com.example.android_jetpack_compose.ui.dashboard.AppBar
import com.example.android_jetpack_compose.util.*
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyExpenseView(date: Date) {
    val viewModel: DailyExpenseViewModel =
        DailyExpenseViewModel(date = date)
    val expenseListState = viewModel.expenseList.observeAsState()
    val totalExpenseState = viewModel.totalMoney.observeAsState()
    val now = Date()
    val title = SimpleDateFormat("dd-MM-yyyy").format(date)
    Scaffold(
        topBar = {
            AppBar(
                title = if (title == SimpleDateFormat("dd-MM-yyyy").format(now)) "Today" else title,
                showBackButton = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                appNavController?.navigate(
                    InputDailyExpense.route.replace(
                        oldValue = "{date}",
                        newValue = viewModel.date.time.toString(),
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
                                appNavController?.navigate(
                                    UpdateDailyExpense.route.replace(
                                        oldValue = "{id}",
                                        newValue = it.id,
                                    ).replace(
                                        oldValue = "{date}",
                                        newValue = viewModel.date.time.toString(),
                                    )
                                )
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
