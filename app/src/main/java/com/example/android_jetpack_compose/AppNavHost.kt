package com.example.android_jetpack_compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_jetpack_compose.ui.views.CalendarHistoryView
import com.example.android_jetpack_compose.ui.views.ChartView
import com.example.android_jetpack_compose.ui.dashboard.DashBoardView
import com.example.android_jetpack_compose.ui.views.MainView
import com.example.android_jetpack_compose.ui.views.NotificationView
import com.example.android_jetpack_compose.ui.views.SettingView

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = Main.route,
        modifier = modifier,
    ) {
        composable(Main.route) {
            MainView()
        }
        composable(Dashboard.route) {
            DashBoardView()
        }
        composable(Calendar.route) {
            CalendarHistoryView()
        }
        composable(Chart.route) {
            ChartView()
        }
        composable(Setting.route) {
            SettingView()
        }
        composable(Notification.route) {
            NotificationView()
        }
    }
}
