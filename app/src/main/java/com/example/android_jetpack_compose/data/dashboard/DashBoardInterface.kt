package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.*

interface GetDateExpenses {
    suspend fun getDateExpenses(): List<DateExpense>
}

interface GetTotalExpense {
    suspend fun getTotalExpense(): Long
}

interface GetProgressData {
    suspend fun getProgressData(): DatesTrackerInfoModel
}

interface ProgressExpenseRepository : GetDateExpenses, GetTotalExpense, GetProgressData
