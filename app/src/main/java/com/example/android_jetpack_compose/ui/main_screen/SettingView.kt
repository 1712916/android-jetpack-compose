package com.example.android_jetpack_compose.ui.main_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.data.login.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.ui.dashboard.view.*
import com.example.android_jetpack_compose.ui.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingView(navController: NavController) {
    val composableScope = rememberCoroutineScope()

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
                item {
                    HeightBox(height = 16.0)
                }
                item {
                    SettingCard(title = "Budget", onClick = {
                        navController.navigate(SettingBudgetExpense.route)
                    })
                }
                item {
                    HeightBox(height = 16.0)
                }
                item {
                    SettingCard(title = "Schedule Remind Enter Data", onClick = {
                        navController.navigate(SettingRemindEnterDailyExpense.route)
                    })
                }
                item {
                    HeightBox(height = 16.0)
                }
                item {
                    SettingCard(title = "Logout", onClick = {
                        val logoutRepository: AuthRepository = AuthRepositoryImpl()
                        composableScope.launch {
                            logoutRepository.logout()
                            AppUser.getInstance().clear()
                            navController.navigate(Login.route) {
                                popUpTo(0)
                            }
                        }
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
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
