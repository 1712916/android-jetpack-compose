package com.example.android_jetpack_compose.ui.dashboard.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.dashboard.view_model.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.util.*
import kotlinx.datetime.*

@Composable
fun CustomLinearIndicator(progress: Float) {
    val borderRadius = 8.0

    Box(
        modifier = Modifier
            .background(color = grayColor, shape = RoundedCornerShape(borderRadius.dp))
            .height(8.dp)
            .clip(RoundedCornerShape(borderRadius.dp))
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(color = orangeColor, shape = RoundedCornerShape(borderRadius.dp))
                .height(8.dp)
                .clip(RoundedCornerShape(borderRadius.dp))
                .fillMaxWidth(fraction = progress)
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekTrackerInfo(
    date: LocalDate,
    onTrackColumnTap: (LocalDate) -> Unit,
) {
    val viewModel: WeekTrackerInfoViewModel =
        viewModel(factory = WeekTrackerInfoViewModelViewModelFactory(date = date)) //todo pass date to here

    val weekTrackerInfoState by viewModel.weekTrackerInfoState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        // Do something with your state
        // You may want to use DisposableEffect or other alternatives
        // instead of LaunchedEffect
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                viewModel.loadData()
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = weekTrackerInfoState.totalSpend.money(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 34.sp,
            color = accentColor,
            maxLines = 1,
        )
        Row {
            Text(
                text = "Total spent this week",
                color = textGrayColor,
            )
            Icon(
                imageVector = weekTrackerInfoState.differentEnum.getIcon(),
                contentDescription = "",
                tint = weekTrackerInfoState.differentEnum.getColor(),
            )
            Text(
                text = "${String.format("%.2f", weekTrackerInfoState.differenceNumber)}%",
                fontWeight = FontWeight.ExtraBold,
                color = weekTrackerInfoState.differentEnum.getColor(),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        //Cần tìm cách lấy thời gian cho mọi SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            weekTrackerInfoState.weekTackers?.let {
                WeekTracker(
                    weekTrackerData = it,
                    dayBudget = weekTrackerInfoState.budget,
                    onTrackColumnTap = onTrackColumnTap,
                )
            }
        }
    }
}
@Composable
fun WeekTracker(
    weekTrackerData: Array<WeekTrackerModel>, dayBudget: Long,
    onTrackColumnTap: (LocalDate) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        weekTrackerData.forEach { item ->
            Box(
                modifier = Modifier.clickable {
                    onTrackColumnTap(item.date)
                }
            ) {
                TrackingColum(
                    percent = (item.dateSpend * 1.0 / dayBudget),
                    title = item.date.dayOfWeek.name.substring(
                        0,
                        minOf(3, item.date.dayOfWeek.name.length)
                    )
                )
            }
        }
    }
}
@Composable
private fun TrackingColum(
    height: Double? = null,
    width: Double? = null,
    percent: Double = 0.0,
    title: String? = null,
) {
    val trackColumnHeight: Double = 150.0
    val trackColumnWidth: Double = 40.0
    val finalHeight = height ?: trackColumnHeight
    val finalWidth = width ?: trackColumnWidth
    val borderRadius = 8.0
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = grayColor, shape = RoundedCornerShape(borderRadius.dp))
                .height(finalHeight.dp)
                .width(finalWidth.dp)
                .clip(RoundedCornerShape(borderRadius.dp))
        ) {
            Box(
                modifier = Modifier
                    .background(color = if (percent > 1.0) redColor else primaryColor)
                    .height((finalHeight * percent).dp)
                    .width(finalWidth.dp)
                    .align(alignment = Alignment.BottomCenter),
            )
        }

        if (title != null)
            Text(
                text = title,
                color = textGrayColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
    }
}
