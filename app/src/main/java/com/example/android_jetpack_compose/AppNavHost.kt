package com.example.android_jetpack_compose

import android.os.*
import androidx.annotation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.daily_expense.view.InputDailyExpenseView
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.main_screen.CalendarHistoryView
import com.example.android_jetpack_compose.ui.main_screen.ChartView
import com.example.android_jetpack_compose.ui.dashboard.view.DashBoardView
import com.example.android_jetpack_compose.ui.expense.view.*
import com.example.android_jetpack_compose.ui.main_screen.MainView
import com.example.android_jetpack_compose.ui.main_screen.NotificationView
import com.example.android_jetpack_compose.ui.main_screen.SettingView
import com.example.android_jetpack_compose.ui.method.view.*
import com.example.android_jetpack_compose.ui.setting_budget.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view.*
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
            MainView(navController)
        }
        composable(Dashboard.route) {
            DashBoardView(navController)
        }
        composable(Calendar.route) {
            CalendarHistoryView(navController)
        }
        composable(Chart.route) {
            ChartView(navController)
        }
        composable(Setting.route) {
            SettingView(navController)
        }
        composable(Notification.route) {
            NotificationView(navController)
        }
        composable(
            DailyExpense.route,
            arguments = listOf(navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            val date = Date(backStackEntry.arguments?.getString("date")!!.toLong())
            val viewModel: DailyExpenseListViewModel =
                viewModel(factory = DailyExpenseListViewModelFactory(date))
            //                DailyExpenseListViewModelImpl(date = date)
            DailyExpenseView(navController, date, viewModel)
        }
        composable(
            InputDailyExpense.route,
            arguments = listOf(navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            InputDailyExpenseView(
                navController,
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
                navController,
                backStackEntry.arguments?.getString("id"),
                Date(backStackEntry.arguments?.getString("date")!!.toLong())
            )
        }
        composable(
            ManagementMethodExpense.route,
        ) {
            MethodScreen(navController)
        }
        composable(
            ManagementCategoryExpense.route,
        ) {
            CategoryScreen(navController)
        }
        composable(
            SettingDefaultExpense.route,
        ) {
            SettingDefaultExpenseView(navController)
        }
        composable(
            SettingInputDefaultExpense.route,
        ) {
            InputDefaultDailyExpenseView(navController)
        }
        composable(
            SettingBudgetExpense.route,
        ) {
            SettingBudgetView()
        }
    }
}
@Composable
fun Test() {
}
