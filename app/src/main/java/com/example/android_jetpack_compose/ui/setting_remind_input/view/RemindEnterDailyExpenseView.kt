package com.example.android_jetpack_compose.ui.setting_remind_input.view

import android.app.*
import android.content.*
import android.os.*
import android.util.*
import android.widget.*
import androidx.annotation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.core.app.*
import androidx.navigation.*
import com.example.android_jetpack_compose.util.*
import java.time.*

const val scheduleKey: String = "scheduleKey"
//value: "hour-minute"
data class AlarmItem(
    val alarmTime: LocalDateTime,
    val message: String
)

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}

private val ALARM_INTERVAL = 1 * 60 * 500L

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarmItem.message)
        }
        val alarmTime =
            alarmItem.alarmTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000L
        Log.e("Alarm", "Alarm set at $alarmTime")
        //        alarmManager.setExactAndAllowWhileIdle(
        //            AlarmManager.RTC_WAKEUP,
        //            alarmTime,
        //            PendingIntent.getBroadcast(
        //                context,
        //                alarmItem.hashCode(),
        //                intent,
        //                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        //            )
        //        )
        //val triggerAtMillis = System.currentTimeMillis() + ALARM_INTERVAL
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            AlarmManager.INTERVAL_HOUR,
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindEnterDailyExpenseView(navController: NavController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
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
            Button(onClick = {
                val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)

                preferencesManager.saveData(
                    scheduleKey,
                    "${timePickerState.hour}-${timePickerState.minute}"
                )
                val dateTimeWithHourAndMinute = LocalDateTime.now()
                    .with(LocalTime.of(timePickerState.hour, timePickerState.minute))

                AlarmItem(
                    alarmTime = LocalDateTime.now().plusSeconds(10),
                    message = "Đến giờ nhập chi tiêu rồi!"
                ).let(alarmScheduler::schedule)
            }) {
                Text(text = "Save")
            }
        }
    }
}
