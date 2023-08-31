package com.example.android_jetpack_compose.ui.views

import android.os.Build
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.android_jetpack_compose.Notification
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.appNavController
import com.example.android_jetpack_compose.ui.theme.AndroidjetpackcomposeTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

val primaryColor = Color(0xFF081f68)
val accentColor = Color(0xFF2b71da)
val grayColor = Color(0xFFd9dae3)
val textGrayColor = Color(0xFF61626d)
val textGreenColor = Color(0xFF4dae15)
val textBlackColor = Color(0xFF101226)
val orangeColor = Color(0xFFfdaa4a)
val borderRadius8 = 8.dp

@Composable
fun HeightBox(height: Double) = Spacer(modifier = Modifier.height(height.dp))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardView() {
    val contextForToast = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(title = "Overview",
                actions = {
                    IconButton(onClick =
                    {
                        appNavController?.navigate(Notification.route)
//                        Toast.makeText(
//                            contextForToast,
//                            "This function is coming soon!",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }) {
                        Icon(
//                            imageVector = Icons.Filled.Notifications,
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_notification),
                            contentDescription = "Notifications",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                TrackingProgressInfo()
            }
        },
    )
}

@Composable
private fun TrackingProgressInfo() {
    Column(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .background(color = Color(0xFFe6e7ee), shape = RoundedCornerShape(8.dp))
    ) {
        // week tracking
        WeekTrackerInfo(
            weekTrackerInfoModel = WeekTrackerInfoModel(
                differenceNumber = 13.0,
                differentEnum = DifferentEnum.Increase,
                totalSpend = 689.50,
                weekTackers = arrayOf(
                    WeekTrackerModel(
                        date = Calendar.getInstance().time,
                        dateBudget = 100.0,
                        dateSpend = 50.0
                    ),
                    WeekTrackerModel(
                        date = Calendar.getInstance().time,
                        dateBudget = 100.0,
                        dateSpend = 50.0
                    ),
                    WeekTrackerModel(
                        date = Calendar.getInstance().time,
                        dateBudget = 100.0,
                        dateSpend = 50.0
                    ),
                    WeekTrackerModel(
                        date = Calendar.getInstance().time,
                        dateBudget = 100.0,
                        dateSpend = 50.0
                    ),
                    WeekTrackerModel(
                        date = Calendar.getInstance().time,
                        dateBudget = 100.0,
                        dateSpend = 50.0
                    ),
                ),
            )
        )
        //month budget
        MonthBudgetProgress()
    }
}

@Composable
private fun MonthBudgetProgress() {
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

fun Double.format(digits: Int) = "%.${digits}f".format(this)

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
private fun WeekTrackerInfo(weekTrackerInfoModel: WeekTrackerInfoModel) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            .height(IntrinsicSize.Min),
    ) {
        Text(
            text = "$${weekTrackerInfoModel.totalSpend.format(2)}",
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
            WeekTracker(
                weekTrackerData = weekTrackerInfoModel.weekTackers,
            )
        }
    }
}

data class WeekTrackerModel(val date: Date, val dateBudget: Double, val dateSpend: Double)
data class WeekTrackerInfoModel(
    val totalSpend: Double,
    val differenceNumber: Double,
    val differentEnum: DifferentEnum,
    val weekTackers: Array<WeekTrackerModel>,
)

enum class DifferentEnum {
    Increase, Decrease
}

@Composable
fun WeekTracker(weekTrackerData: Array<WeekTrackerModel>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        weekTrackerData.forEach { item ->
            TrackingColum(
                percent = item.dateSpend / item.dateBudget,
                title = SimpleDateFormat("EEE").format(item.date)
            )
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
                    .background(color = primaryColor)
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

@Composable
fun SimpleColumn() {
    Column {
        Text(
            text = "Column Text 1",
            Modifier.background(Color.Red)
        )
        Text(text = "Column Text 2", Modifier.background(Color.White))
        Text(text = "Column Text 3", Modifier.background(Color.Green))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, actions: @Composable RowScope.() -> Unit = {}) {
    // TopAppBar Composable
    TopAppBar(
        // Provide Title
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        actions = actions
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidjetpackcomposeTheme {
        DashBoardView()
    }
}
