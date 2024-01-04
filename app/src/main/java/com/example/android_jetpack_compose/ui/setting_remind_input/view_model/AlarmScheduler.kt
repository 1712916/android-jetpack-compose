package com.example.android_jetpack_compose.ui.setting_remind_input.view_model

import android.app.*
import android.content.*
import com.example.android_jetpack_compose.ui.setting_remind_input.alarm.*
import java.util.*

const val scheduleKey: String = "scheduleKey"
private const val requestCode = 1512
//value: "hour-minute"
data class AlarmItem(
    var hour: Int,
    var minute: Int,
    val message: String
)

interface AlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel()
}

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarmItem.message)
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, alarmItem.hour)
            set(Calendar.MINUTE, alarmItem.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            // If alarm time has already passed, increment day by 1
            if (timeInMillis <= System.currentTimeMillis()) {
                set(Calendar.DAY_OF_MONTH, get(Calendar.DAY_OF_MONTH) + 1)
            }
        }


        alarmManager.setRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                requestCode,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
