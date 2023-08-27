package com.example.android_jetpack_compose.router

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.android_jetpack_compose.R

sealed class Screen(val route: String, @StringRes val labelId: Int, @DrawableRes val iconId: Int) {
    object Main : Screen("main", -1, -1)
    object Dashboard : Screen("dashboard", R.string.btn_dashboard, R.drawable.ic_dashboard)
    object Calendar : Screen("calendar", R.string.btn_calendar, R.drawable.ic_calendar)
    object Chart : Screen("chart", R.string.btn_chart, R.drawable.ic_chart)
    object Setting : Screen("setting", R.string.btn_setting, R.drawable.ic_setting)
    object Notification : Screen("notification", -1, -1)
}
