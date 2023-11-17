package com.example.android_jetpack_compose

import android.os.*
import androidx.annotation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.daily_expense.view.InputDailyExpenseView
import com.example.android_jetpack_compose.ui.main_screen.CalendarHistoryView
import com.example.android_jetpack_compose.ui.main_screen.ChartView
import com.example.android_jetpack_compose.ui.dashboard.DashBoardView
import com.example.android_jetpack_compose.ui.main_screen.MainView
import com.example.android_jetpack_compose.ui.main_screen.NotificationView
import com.example.android_jetpack_compose.ui.main_screen.SettingView
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(
            DailyExpense.route,
            arguments = listOf(navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            DailyExpenseView(Date(backStackEntry.arguments?.getString("date")!!.toLong()))
        }
        composable(
            InputDailyExpense.route,
            arguments = listOf(navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            InputDailyExpenseView(
                Date(backStackEntry.arguments?.getString("date")!!.toLong())
            )
        }
        composable(
            UpdateDailyExpense.route,
            arguments = listOf(
                navArgument("id") { defaultValue = "" },
                navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            UpdateDailyExpenseView(
                backStackEntry.arguments?.getString("id"),
                Date(backStackEntry.arguments?.getString("date")!!.toLong())
            )
        }
    }
}
