package com.example.android_jetpack_compose.ui.dashboard.view

import android.os.Build
import android.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import com.example.android_jetpack_compose.entity.WeekTrackerModel
import com.example.android_jetpack_compose.ui.dashboard.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardView(
    navController: NavController,
    viewModel: WeekTrackerInfoViewModel = viewModel()
) {
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
    Scaffold(
        topBar = {
            AppBar(
                navController = navController,
                title = "Overview",
                actions = {
                    IconButton(onClick =
                    {
                        navController.navigate(Notification.route)
                    }) {
                        Icon(
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
                TrackingProgressInfo(
                    weekTrackerInfoState,
                    { date ->
                        navController.navigate(
                            DailyExpense.route.replace(
                                oldValue = "{date}",
                                newValue = date.time.toString(),
                            )
                        )
                    }
                )
            }
        },
    )
}
