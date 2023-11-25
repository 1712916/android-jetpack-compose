package com.example.android_jetpack_compose.ui.dashboard.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.entity.*
import java.util.*

@Composable
fun TrackingProgressInfo(
    weekTrackerInfoModel: WeekTrackerInfoModel,
    onTrackColumnTap: (Date) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .background(color = Color(0xFFe6e7ee), shape = RoundedCornerShape(8.dp))
    ) {
        // week tracking
        WeekTrackerInfo(
            weekTrackerInfoModel = weekTrackerInfoModel,
            onTrackColumnTap = onTrackColumnTap,
        )
        //month budget
        MonthBudgetProgress()
    }
}
