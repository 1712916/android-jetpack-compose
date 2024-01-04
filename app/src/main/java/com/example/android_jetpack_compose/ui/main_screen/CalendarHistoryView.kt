package com.example.android_jetpack_compose.ui.main_screen

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.calendar.*
import com.example.android_jetpack_compose.ui.view.*
import io.github.boguszpawlowski.composecalendar.*
import io.github.boguszpawlowski.composecalendar.selection.*
import java.time.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarHistoryView(navController: NavController) {
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Single,
        confirmSelectionChange = {
            if (it.isNotEmpty()) {
                navController.navigate(
                    DailyExpense.route.replace(
                        oldValue = "{date}",
                        newValue = Date.from(
                            it[0].atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ).time.toString(),
                    )
                )
            }
            false
        }
    )




    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = "Calendar"
            )
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            CalendarView()
            //            SelectableCalendar(
            //                calendarState = calendarState,
            //            )
        }
    }
}
