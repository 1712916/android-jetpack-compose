package com.example.android_jetpack_compose.ui.chart.view

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.*
import com.example.android_jetpack_compose.ui.chart.view_model.*
import com.example.android_jetpack_compose.ui.view.chart.*

@Composable
fun MonthChart() {
    val viewModel: MonthChartViewModel = viewModel()
    val moneys by viewModel.uiState.collectAsState()
    PieChart(
        data = moneys
    )
}
