package com.example.android_jetpack_compose.ui.main_screen

import android.os.*
import android.util.*
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.annotation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.dashboard.AppBar
import io.github.boguszpawlowski.composecalendar.*
import io.github.boguszpawlowski.composecalendar.selection.*
import kotlinx.datetime.*
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
            SelectableCalendar(
                calendarState = calendarState,
            )
        }
    }
}
