@file:OptIn(ExperimentalFoundationApi::class)

package com.example.android_jetpack_compose.ui.main_screen

import android.os.*
import android.util.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.Calendar
import com.example.android_jetpack_compose.ui.dashboard.view.*
import com.example.android_jetpack_compose.ui.setting_remind_input.view_model.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import java.time.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(navController: NavController) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 4 })
    // scroll to page
    val coroutineScope = rememberCoroutineScope()
    suspend fun onBottomNavTap(screen: BottomNavigationItem) {
        when (screen) {
            Dashboard -> pagerState.scrollToPage(0)
            Calendar -> pagerState.scrollToPage(1)
            Chart -> pagerState.scrollToPage(2)
            Setting -> pagerState.scrollToPage(3)
            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        InitAppRoute.instance.navToInitRoute(navController)
    }


    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Log.d("Page change", "Page changed to $page")
        }
    }

    LaunchedEffect(Unit) {
        //Retrieve the reminder time:

        val preferencesManager = SharedPreferencesManager(context)
        val data = preferencesManager.getData(scheduleKey, "0-0")
        val hour = data.toString().split("-").first().toInt()
        val minute = data.toString().split("-").last().toInt()

        val dateTimeWithHourAndMinute = LocalDateTime.now()
            .with(LocalTime.of(hour, minute))
        val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)

        AlarmItem(
            alarmTime = dateTimeWithHourAndMinute,
            message = "Đến giờ nhập chi tiêu rồi!"
        ).let(alarmScheduler::schedule)
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        DailyExpense.route.replace(
                            oldValue = "{date}",
                            newValue = Date().time.toString(),
                        )
                    )
                },
                containerColor = accentColor,
                modifier = Modifier.width(100.dp)
            ) {
                Icon(
                    Icons.Outlined.Add, "Floating action button.",
                    modifier = Modifier.background(color = Color.White)
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                bottomNavScreens.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = screen.iconId),
                                contentDescription = "Notifications",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        },
                        //  label = { Text(stringResource(screen.labelId)) },
                        selected = true,
                        onClick = {
                            coroutineScope.launch {
                                // Call scroll to on pagerState
                                onBottomNavTap(screen)
                            }
                        }
                    )
                }
            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            HorizontalPager(state = pagerState) { page ->
                // Our page content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    when (page) {
                        0 -> DashBoardView(navController)
                        1 -> CalendarHistoryView(navController)
                        2 -> ChartView(navController)
                        3 -> SettingView(navController)
                    }
                }
            }
        }
    }
}
