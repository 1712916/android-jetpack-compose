package com.example.android_jetpack_compose.ui.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import com.example.android_jetpack_compose.ui.chart.view.*
import com.example.android_jetpack_compose.ui.view.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartView(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = "Chart"
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            item {
                MonthChart()
            }
        }
    }
}
