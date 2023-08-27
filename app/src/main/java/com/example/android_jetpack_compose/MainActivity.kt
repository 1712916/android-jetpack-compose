package com.example.android_jetpack_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.ui.theme.AndroidjetpackcomposeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_jetpack_compose.views.DashBoardView
import com.example.android_jetpack_compose.views.MainView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_jetpack_compose.router.Screen
import com.example.android_jetpack_compose.views.CalendarHistoryView
import com.example.android_jetpack_compose.views.ChartView
import com.example.android_jetpack_compose.views.NotificationView
import com.example.android_jetpack_compose.views.SettingView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidjetpackcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigateView()
                }
            }
        }
    }
}

var appNavController : NavHostController? = null

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NavigateView() {
    val navController = rememberNavController()
    appNavController = navController

    Scaffold { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Main.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Main.route) {
                MainView()
            }
            composable(Screen.Dashboard.route) {
                DashBoardView()
            }
            composable(Screen.Calendar.route) {
                CalendarHistoryView()
            }
            composable(Screen.Chart.route) {
                ChartView()
            }
            composable(Screen.Setting.route) {
                SettingView()
            }
            composable(Screen.Notification.route) {
                NotificationView()
            }
        }
    }
}
