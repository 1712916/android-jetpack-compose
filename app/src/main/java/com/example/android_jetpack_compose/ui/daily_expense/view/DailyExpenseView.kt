package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.InputDailyExpense
import com.example.android_jetpack_compose.Notification
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.appNavController
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.ui.dashboard.AppBar
import java.util.Date

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyExpenseView() {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppBar(
                title = "",
                actions = {
                    IconButton(onClick =
                    {
                        appNavController?.navigate(Notification.route)
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                            contentDescription = "Notifications",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                },
                showBackButton = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                appNavController?.navigate(InputDailyExpense.route)

            }) {
            }
        }
    ) { p ->
        LazyColumn(
            modifier = Modifier.padding(p),
            content = {
                item {
                    TotalExpenseView(10000)
                }

                item {
                    ExpenseCard(
                        MoneyModel(
                            id = 1,
                    money = 35000,
                    note = null,
                    expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
                    expenseMethod = ExpenseMethod.Cash(),
                    createDate = Date(),
                    updateDate = Date(),
                    )
                    )
                }
                item {
                    Text(text = text)
                }

            },
        )
    }
}

@Composable
fun TotalExpenseView(totalExpense: Long) {
    Column() {
        Text(text = "Total Expense")
        Text(text = "$totalExpense")
    }
}

@Composable
fun ExpenseCard(expense: MoneyModel) {
    Column() {
        Text(text = expense.expenseCategory.name)
        Text(text = expense.expenseMethod.name)
        Text(text = "${expense.money}")
    }
}
