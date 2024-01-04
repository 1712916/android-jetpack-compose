package com.example.android_jetpack_compose.ui.setting_remind_input.view

import android.os.*
import androidx.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.navigation.*
import com.example.android_jetpack_compose.ui.setting_remind_input.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindEnterDailyExpenseView(navController: NavController) {
    val context = LocalContext.current
    val preferencesManager = remember { SharedPreferencesManager(context) }
    val data = preferencesManager.getData(scheduleKey, "0-0")
    val hour = data.toString().split("-").first().toInt()
    val minute = data.toString().split("-").last().toInt()
    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute
    )

    Scaffold { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(state = timePickerState)

            Text(text = "Selected H:M = ${timePickerState.hour} : ${timePickerState.minute}")
            Row {
                Button(onClick = {
                    val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)

                    preferencesManager.saveData(
                        scheduleKey,
                        "${timePickerState.hour}-${timePickerState.minute}"
                    )

                    AlarmItem(
                        timePickerState.hour, timePickerState.minute,
                        message = "Đến giờ nhập chi tiêu rồi!"
                    ).let(alarmScheduler::schedule)

                    ShowToastMessage("Setup remind time successfully").show(context)

                    navController.popBackStack()
                }) {
                    Text(text = "Save")
                }
                SizedBox(width = 8.0)
                Button(onClick = {
                    val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)

                    alarmScheduler.cancel()

                    ShowToastMessage("Cancel current schedule successfully").show(context)

                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}
