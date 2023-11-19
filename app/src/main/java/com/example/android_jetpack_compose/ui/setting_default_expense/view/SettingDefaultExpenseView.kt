package com.example.android_jetpack_compose.ui.setting_default_expense.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view_model.*
import java.text.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingDefaultExpenseView() {
    val viewModel: SettingDefaultExpenseViewModel = viewModel()
    val expenseListState = viewModel.expenseList.observeAsState()
    val totalExpenseState = viewModel.totalMoney.observeAsState()
    val deleteMode = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            AppBar(
                title = "Default Expense",
                showBackButton = true,
                actions = {
                    IconButton(onClick = {
                        deleteMode.value = !deleteMode.value
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
                appNavController?.navigate(
                    SettingInputDefaultExpense.route
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
                                //                                appNavController?.navigate(
                                //                                    UpdateDailyExpense.route.replace(
                                //                                        oldValue = "{id}",
                                //                                        newValue = it.id,
                                //                                    ).replace(
                                //                                        oldValue = "{date}",
                                //                                        newValue = viewModel.date.time.toString(),
                                //                                    )
                                //                                )
                            },
                            showDelete = deleteMode.value,
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
