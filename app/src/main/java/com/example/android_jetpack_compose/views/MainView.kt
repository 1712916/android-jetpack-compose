package com.example.android_jetpack_compose.views

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.router.Screen
import kotlinx.coroutines.launch

val items = listOf(
    Screen.Dashboard,
    Screen.Calendar,
    Screen.Chart,
    Screen.Setting,
)

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainView() {
    val pagerState = rememberPagerState(pageCount = {4})
    // scroll to page
    val coroutineScope = rememberCoroutineScope()

    suspend fun onBottomNavTap(screen: Screen) {
        when (screen) {
            Screen.Dashboard -> pagerState.scrollToPage(0)
            Screen.Calendar -> pagerState.scrollToPage(1)
            Screen.Chart -> pagerState.scrollToPage(2)
            Screen.Setting -> pagerState.scrollToPage(3)
            else -> {}
        }
    }

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Log.d("Page change", "Page changed to $page")
        }
    }


    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = screen.iconId),
                            contentDescription = "Notifications",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    },
                    //  label = { Text(stringResource(screen.labelId)) },
                    selected = true,
                    onClick = {
                        coroutineScope.launch {
                            // Call scroll to on pagerState
                            onBottomNavTap(screen)
                        }
                    }
                )
            }
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            HorizontalPager(state = pagerState) { page ->
                // Our page content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    when (page){
                        0 -> DashBoardView( )
                        1 -> CalendarHistoryView( )
                        2 -> ChartView( )
                        3 -> SettingView( )
                    }
                }
            }
        }
    }
}
