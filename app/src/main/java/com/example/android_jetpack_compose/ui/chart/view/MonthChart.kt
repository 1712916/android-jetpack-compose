package com.example.android_jetpack_compose.ui.chart.view

import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.*
import com.example.android_jetpack_compose.ui.chart.view_model.*
@Composable
fun MonthChart() {
    val viewModel: MonthChartViewModel = viewModel()
    val moneys by viewModel.uiState.collectAsState()
    LazyColumn {
        items(moneys.keys.toList()) { category ->
            Text("${category}: ${moneys.get(category)}")
        }
    }
}
