package com.example.android_jetpack_compose.ui.dashboard.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.ui.view.*
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.*
import kotlinx.datetime.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackingProgressInfo(
    onTrackColumnTap: (LocalDate) -> Unit,
) {
    var date by remember { mutableStateOf(LocalDate.now()) }

    Column {
        Column(
            modifier = Modifier
                .padding(PaddingValues(16.dp))
                .background(color = Color(0xFFe6e7ee), shape = RoundedCornerShape(8.dp))
        ) {
            // week tracking
            WeekTrackerInfo(
                date = date,
                onTrackColumnTap = onTrackColumnTap
            )
            WeekPaginationView(onChanged = { start, end ->

                date = start
            })
        }
    }
}
