package com.example.android_jetpack_compose.ui.dashboard.view

import android.os.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*
import java.text.*
import java.util.*

@Composable
fun MonthBudgetProgress() {
    Column(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .shadow(
                elevation = 8.dp, shape = RoundedCornerShape(8.dp)
            )
            .background(color = Color.White)
            .padding(PaddingValues(16.dp))
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "Budget for this month",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = textBlackColor,
            )
            Text(
                "$2.400",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = accentColor,
            )
        }
        //                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(height = 8.dp).clip(RoundedCornerShape(borderRadius8)),
        //                        color = orangeColor,
        //                        trackColor = grayColor,
        //                        progress = 0.7f,
        //                    )
        HeightBox(12.0)
        CustomLinearIndicator(progress = 0.7f)
        HeightBox(6.0)
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = textGrayColor)) {
                    append("Spent ")
                }
                withStyle(
                    style = SpanStyle(
                        color = textBlackColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("$1.800")
                }

                withStyle(style = SpanStyle(color = textGrayColor)) {
                    append(" of $2.400")
                }
            }
        )
    }
}
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
@Composable
fun WeekTrackerInfo(
    weekTrackerInfoModel: WeekTrackerInfoModel,
    onTrackColumnTap: (Date) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = weekTrackerInfoModel.totalSpend.money(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 34.sp,
            color = accentColor,
        )
        Row {
            Text(
                text = "Total spent this week",
                color = textGrayColor,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = textGreenColor,
            )
            Text(
                text = "${weekTrackerInfoModel.differenceNumber}%",
                fontWeight = FontWeight.ExtraBold,
                color = textGreenColor,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        //Cần tìm cách lấy thời gian cho mọi SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            weekTrackerInfoModel.weekTackers?.let {
                WeekTracker(
                    weekTrackerData = it,
                    dayBudget = weekTrackerInfoModel.dayBudget,
                    onTrackColumnTap = onTrackColumnTap,
                )
            }
        }
    }
}
@Composable
fun WeekTracker(
    weekTrackerData: Array<WeekTrackerModel>, dayBudget: Long,
    onTrackColumnTap: (Date) -> Unit,
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
                    title = SimpleDateFormat("EEE", Locale.getDefault()).format(item.date)
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
