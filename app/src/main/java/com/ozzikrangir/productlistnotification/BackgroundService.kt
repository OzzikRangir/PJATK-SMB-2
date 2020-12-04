package com.ozzikrangir.productlistnotification

import android.app.*
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class BackgroundService : Service() {

    private val channelId = "productlistChannel"
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
        }
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationId: Int = Random.nextInt()
        intent.component = ComponentName(
            "com.ozzikrangir.productlist",
            "com.ozzikrangir.productlist.ui.main.MainActivity"
        )
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        println(intent.extras)
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder= NotificationCompat.Builder(this, channelId)
            .setContentTitle("Added new product:")
            .setContentText(intent.getStringExtra("productName"))
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setContentIntent(pendingIntent)
        mBuilder.build().flags.and(Notification.FLAG_AUTO_CANCEL)

        startForeground(1, mBuilder.build())
        this.stopForeground(false);
        return START_NOT_STICKY
    }

}