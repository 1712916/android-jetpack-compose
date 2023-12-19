package com.example.android_jetpack_compose.ui.setting_remind_input.view_model

import android.app.*
import android.content.*
import android.os.*
import android.util.*
import androidx.annotation.*
import com.example.android_jetpack_compose.ui.setting_remind_input.view.*
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
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            AlarmManager.INTERVAL_DAY,
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
