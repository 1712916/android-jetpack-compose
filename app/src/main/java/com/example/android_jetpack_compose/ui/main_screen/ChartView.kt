package com.example.android_jetpack_compose.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import com.example.android_jetpack_compose.ui.dashboard.AppBar

 @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartView(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = "Chart")
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
        }
    }
}
