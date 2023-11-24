package com.example.android_jetpack_compose.ui.setting_budget.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.setting_budget.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import kotlinx.coroutines.flow.*
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBudgetView(navController: NavController) {
    val viewModel: BudgetViewModel = viewModel()
    val budgetState = viewModel.budgetState.collectAsState()
    var monthPosition = viewModel.monthPosition.collectAsState()
    var dayPosition = viewModel.dayPosition.collectAsState()

    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "Budget")
        }
    ) { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                item {
                    Column {
                        Text("Month Budget")
                        HeightBox(height = 8.0)
                        MoneyInputView(
                            number = budgetState.value.month.toString(),
                            validateState = InputMoneyValidator(
                                source = "0",
                                destination = "0",
                            ).validate()
                        )
                        CustomSlider(
                            value = monthPosition.value,
                            onValueChange = {
                                viewModel.monthPosition.value = it.toFloat()
                                viewModel.updateMonthBudget(
                                    (it.toLong())
                                )
                            },
                            maxRange = 100,
                        )
                    }
                }
                item {
                    Column {
                        Text("Day Budget")
                        HeightBox(height = 8.0)
                        MoneyInputView(
                            number = budgetState.value.day.toString(),
                            validateState = InputMoneyValidator(
                                source = "0",
                                destination = "0",
                            ).validate()
                        )
                        CustomSlider(
                            value = dayPosition.value,
                            onValueChange = {
                                viewModel.dayPosition.value = it.toFloat()
                                viewModel.updateDayBudget(
                                    (it.toLong())
                                )
                            },
                            maxRange = 100,
                        )
                    }
                }
            }
            Button(
                onClick = {
                    viewModel.onSave()
                },
            ) {
                Text("Save")
            }
        }
    }
}
@Composable
fun CustomSlider(
    value: Float,
    maxRange: Int,
    onValueChange: (Int) -> Unit,
) {
    Slider(
        value = value,
        onValueChange = {
            onValueChange(it.toDouble().roundToInt())
        },
        steps = maxRange - 1,
        valueRange = 0f..maxRange.toFloat()
    )
}
