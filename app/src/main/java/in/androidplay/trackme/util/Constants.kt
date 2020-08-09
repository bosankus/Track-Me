package `in`.androidplay.trackme.util

import `in`.androidplay.trackme.R
import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 7:57 AM
 */
@SuppressLint("InlinedApi")
object Constants {

    const val GLOBAL_TAG = "Androidplay"
    const val RUNNING_DATABASE_NAME = "running_db"


    // SDK versions

    const val ANDROID_OREO = Build.VERSION_CODES.O
    const val ANDROID_10 = Build.VERSION_CODES.Q



    // Service constants

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"


    // Permission constants

    const val PERMISSION_REQUEST_RATIONAL =
        "You need to accept location permissions to use this app"
    const val PERMISSION_REQUEST_CODE = 100

    const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"


    // Notification constants

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    const val NOTIFICATION_GROUP_ID = "workout_group"
    const val NOTIFICATION_GROUP_NAME = "Workout"


    // Location constants

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L


    // Polyline constants

    const val POLYLINE_COLOR = Color.BLACK
    const val POLYLINE_WIDTH = 9f
    const val MAP_CAMERA_ZOOM = 17f


    // Stopwatch constants

    const val TIMER_UPDATE_INTERVAL = 50L
}