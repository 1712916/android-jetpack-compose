package com.example.android_jetpack_compose.ui.dashboard.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.*
import com.example.android_jetpack_compose.ui.dashboard.view_model.*
import com.example.android_jetpack_compose.ui.theme.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*

@Composable
fun MonthBudgetProgress(viewModel: MonthProgressInfoViewModel = viewModel()) {
    val monthTackerInfoState by viewModel.monthTackerInfoState.collectAsState()
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

    fun getMonthBudget(): String {
        return monthTackerInfoState.budget.money() + "VND"
    }

    fun getMonthTotalSpend(): String {
        return monthTackerInfoState.totalSpend.money() + "VND"
    }

    fun getPercentProgress(): Float {
        if (monthTackerInfoState.budget == 0L) {
            return 0f
        }

        return (monthTackerInfoState.totalSpend * 1f) / monthTackerInfoState.budget

    }
    Column(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .padding(PaddingValues(16.dp))
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "Month Budget",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = textBlackColor,
            )
        }
        HeightBox(12.0)
        CustomLinearIndicator(progress = getPercentProgress())
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
                    append("${getMonthTotalSpend()}")
                }

                withStyle(
                    style = SpanStyle(
                        color = accentColor, fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                    )
                ) {
                    append(" of ${getMonthBudget()}")
                }
            }
        )
    }
}
//total spend
//month budget
