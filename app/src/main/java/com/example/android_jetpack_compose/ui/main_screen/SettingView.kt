package com.example.android_jetpack_compose.ui.main_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.dashboard.AppBar

 @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingView(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = "Setting",
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    SettingCard(title = "Default Daily", onClick = {
                        navController.navigate(SettingDefaultExpense.route)
                    })
                }
            }
        }
    }
}
@Composable
fun SettingCard(title: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .clickable {
            onClick()
        }
    ) {
        Box(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.Start)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
