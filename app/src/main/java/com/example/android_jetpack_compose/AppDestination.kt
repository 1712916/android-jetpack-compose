package com.example.android_jetpack_compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface AppDestination {
    val route: String
}

interface BottomNavigationItem {
    @get:StringRes
    val labelId: Int
    @get:DrawableRes
    val iconId: Int
}
/**
 * App navigation destinations
 */
object Main : AppDestination {
    override val route = "main"
}

object Notification : AppDestination {
    override val route = "notification"
}

object Dashboard : AppDestination, BottomNavigationItem {
    override val route = "dashboard"
    override val labelId = R.string.btn_dashboard
    override val iconId = R.drawable.ic_dashboard
}

object Calendar : AppDestination, BottomNavigationItem {
    override val route = "calendar"
    override val labelId = R.string.btn_calendar
    override val iconId = R.drawable.ic_calendar
}

object Chart : AppDestination, BottomNavigationItem {
    override val route = "calendar"
    override val labelId = R.string.btn_chart
    override val iconId = R.drawable.ic_chart
}

object Setting : AppDestination, BottomNavigationItem {
    override val route = "calendar"
    override val labelId = R.string.btn_setting
    override val iconId = R.drawable.ic_setting
}
// Screens to be displayed in the bottom nav
val bottomNavScreens: List<BottomNavigationItem> = listOf(Dashboard, Calendar, Chart, Setting)

object DailyExpense : AppDestination {
    override val route = "daily"
}

object InputDailyExpense : AppDestination {
    override val route = "daily-input"
}

object UpdateDailyExpense : AppDestination {
    override val route = "daily-update?id={id}&date={date}"
}
