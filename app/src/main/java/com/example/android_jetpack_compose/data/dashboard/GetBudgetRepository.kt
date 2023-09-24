package com.example.android_jetpack_compose.data.dashboard

import java.util.Calendar

abstract class GetBudgetRepository {
    abstract fun getBudget(): Double
}

class GetMonthBudgetRepository : GetBudgetRepository() {
    override fun getBudget(): Double {
        return 3000.0
    }
}

class GetDayBudgetRepository : GetBudgetRepository() {
    override fun getBudget(): Double {
        //tính budget của tháng
        val monthBudget = GetMonthBudgetRepository().getBudget()
        //tính số ngày trong tháng
        val numberDateOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)

        return monthBudget / numberDateOfMonth
    }
}
