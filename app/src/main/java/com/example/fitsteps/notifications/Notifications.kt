package com.example.fitsteps.notifications

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fitsteps.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class Notifications: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notification = NotificationCompat.Builder(applicationContext, "common_id")
                    .setContentTitle(message.notification?.title)
                    .setContentText(message.notification?.body)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build()
                notificationManager.notify(1, notification)
            }
        }
        else {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = NotificationCompat.Builder(applicationContext, "common_id")
                .setContentTitle(message.notification?.title)
                .setContentText(message.notification?.body)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()
            notificationManager.notify(1, notification)
        }
    }
}