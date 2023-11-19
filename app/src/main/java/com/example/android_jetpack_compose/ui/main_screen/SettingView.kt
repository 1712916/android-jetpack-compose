package com.example.android_jetpack_compose.ui.main_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.dashboard.AppBar

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingView() {
    Scaffold(
        topBar = {
            AppBar(
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
                        appNavController?.navigate(SettingDefaultExpense.route)
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
        .height(70.dp).clickable {
            onClick()
        }
         ) {
        Box(modifier = Modifier.padding(16.dp).align(Alignment.Start)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
            )
        }

    }
}
