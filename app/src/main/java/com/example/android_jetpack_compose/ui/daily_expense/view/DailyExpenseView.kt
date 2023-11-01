package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_jetpack_compose.Notification
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.appNavController
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.ui.daily_expense.view_model.DailyExpenseViewModel
import com.example.android_jetpack_compose.ui.dashboard.AppBar
import java.util.*

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyExpenseView() {
    val viewModel: DailyExpenseViewModel = viewModel()

    val expenseListState = viewModel.expenseList.observeAsState()

    val totalExpenseState = viewModel.totalMoney.observeAsState()


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
//                appNavController?.navigate(InputDailyExpense.route)

                viewModel.add(
                    MoneyModel(
                        id = 1,
                        money = 70000,
                        note = null,
                        expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
                        expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
                        createDate = Date(),
                        updateDate = Date(),
                    )
                )

            }) {
            }
        }
    ) { p ->
        LazyColumn(
            modifier = Modifier.padding(p),
            content = {
                item {
                    TotalExpenseView(totalExpenseState.value ?: 0)
                }

                item {
                    ExpenseCard(
                        MoneyModel(
                            id = 1,
                            money = 35000,
                            note = null,
                            expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
                            expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
                            createDate = Date(),
                            updateDate = Date(),
                        )
                    )
                }

                items(expenseListState.value?.count() ?: 0) {
                    ExpenseCard(
                        expenseListState.value!![it]
                    )
                }

            },
        )
    }
}

@Composable
fun TotalExpenseView(totalExpense: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart,

        ) {
        Text(
            text = "$totalExpense",
            style = MaterialTheme.typography.bodyLarge
        )

    }


}

@Composable
fun ExpenseCard(expense: MoneyModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.expenseCategory.name.uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = expense.expenseMethod.name)
            }

            Text(
                text = "${expense.money}",
                style = MaterialTheme.typography.titleLarge
            )
            if (!expense.note.isNullOrEmpty())
                Text(
                    text = "${expense.money}",
                    style = MaterialTheme.typography.labelLarge
                )
        }
    }
}
