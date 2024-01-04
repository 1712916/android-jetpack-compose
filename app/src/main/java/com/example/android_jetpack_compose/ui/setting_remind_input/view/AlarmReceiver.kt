package com.example.android_jetpack_compose.ui.setting_remind_input.view

import android.app.*
import android.content.*
import android.os.*
import androidx.core.app.*
import com.example.android_jetpack_compose.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent!!.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                // if phone is restarted
                startRescheduleAlarmsService(context!!)
            }

            else -> {
                val toastText = "Alarm Received"

                val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
                val channelId = "alarm_id"
                context?.let { ctx ->
                    val myIntent = Intent(context, MainActivity::class.java).apply {
                        putExtra("route", DailyExpense.route)
                    }
                    val pendingIntent = PendingIntent.getActivity(
                        context,
                        0,
                        myIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    val notificationManager =
                        ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val builder = NotificationCompat.Builder(ctx, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Money Tracking App")
                        .setContentText("$message")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)

                    notificationManager.notify(1, builder.build())

                }

            }
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, AlarmReceiver::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}
