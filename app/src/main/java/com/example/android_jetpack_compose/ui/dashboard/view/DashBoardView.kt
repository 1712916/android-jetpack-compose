package com.example.android_jetpack_compose.ui.dashboard.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.date.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardView(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            AppBar(
                navController = navController,
                title = "Overview",
                actions = {
                    //                    IconButton(onClick =
                    //                    {
                    //                        navController.navigate(Notification.route)
                    //                    }) {
                    //                        Icon(
                    //                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                    //                            contentDescription = "Notifications",
                    //                            modifier = Modifier
                    //                                .width(24.dp)
                    //                                .height(24.dp)
                    //                        )
                    //                    }
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                //month budget
                MonthBudgetProgress()
                TrackingProgressInfo(
                    { date ->
                        navController.navigate(
                            DailyExpense.route.replace(
                                oldValue = "{date}",
                                newValue = date.formatDayParam(),
                            )
                        )
                    }
                )
            }
        },
    )
}
