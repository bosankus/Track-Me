package `in`.androidplay.trackme.services

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.ui.activity.MainActivity
import `in`.androidplay.trackme.util.Constants.ACTION_PAUSE_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import `in`.androidplay.trackme.util.Constants.ACTION_START_OR_RESUME_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_STOP_SERVICE
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_CHANNEL_ID
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_CHANNEL_NAME
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_GROUP_ID
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_GROUP_NAME
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_ID
import `in`.androidplay.trackme.util.Helper.logMessage
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 12:02 PM
 */
class TrackingService : LifecycleService() {

    var isFirstRun = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        setForegroundService()
                        isFirstRun = false
                    } else {
                        logMessage("Resuming service")
                    }
                }
                ACTION_PAUSE_SERVICE -> logMessage("Pause Service")
                ACTION_STOP_SERVICE -> logMessage("Stop Service")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(
            this,
            NOTIFICATION_CHANNEL_ID
        )
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle("Run time: ")
            .setContentText("00:00:00")
            .setContentIntent(getPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

    }

    private fun getPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        channel.lightColor = Color.YELLOW
        channel.setShowBadge(true)

        val channelGroup = NotificationChannelGroup(
            NOTIFICATION_GROUP_ID,
            NOTIFICATION_GROUP_NAME
        )

        notificationManager.createNotificationChannel(channel)
        notificationManager.createNotificationChannelGroup(channelGroup)
    }
}