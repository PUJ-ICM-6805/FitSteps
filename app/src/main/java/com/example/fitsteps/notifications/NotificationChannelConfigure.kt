package com.example.fitsteps.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class NotificationChannelConfigure() : Application(){
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "common_id",
            "notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}