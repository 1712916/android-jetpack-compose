package com.example.android_jetpack_compose

import android.os.*
import androidx.annotation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.dashboard.view.*
import com.example.android_jetpack_compose.ui.expense.view.*
import com.example.android_jetpack_compose.ui.login.view.*
import com.example.android_jetpack_compose.ui.main_screen.*
import com.example.android_jetpack_compose.ui.method.view.*
import com.example.android_jetpack_compose.ui.setting_budget.view.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view.*
import com.example.android_jetpack_compose.ui.setting_remind_input.view.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController,
        startDestination = Login.route,
        modifier = modifier,
    ) {
        composable(Login.route) {
            LoginPage(navController)
        }
        composable(Register.route) {
            RegisterPage(navController)
        }
        composable(ForgotPassword.route) {
            ForgotPasswordPage(navController)
        }
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
            var date: LocalDate = LocalDate.now()
            val dateString = backStackEntry.arguments?.getString("date")
            if (dateString?.isNotEmpty() == true) {
                try {
                    date = LocalDate.parse(dateString)
                } catch (e: Exception) {
                }
            }
            val viewModel: DailyExpenseListViewModel =
                viewModel(factory = DailyExpenseListViewModelFactory(date))
            DailyExpenseView(navController, date, viewModel)
        }
        composable(
            InputDailyExpense.route,
            arguments = listOf(navArgument("date") { defaultValue = "" })
        ) { backStackEntry ->
            InputDailyExpenseView(
                navController,
                LocalDate.now(),
                //                Date(backStackEntry.arguments?.getString("date")!!.toLong())
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
                LocalDate.now(),
                //                Date(backStackEntry.arguments?.getString("date")!!.toLong())
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
            SettingBudgetView(navController)
        }
        composable(
            SettingRemindEnterDailyExpense.route,
        ) {
            RemindEnterDailyExpenseView(navController)
        }
    }
}
@Composable
fun Test() {
}
