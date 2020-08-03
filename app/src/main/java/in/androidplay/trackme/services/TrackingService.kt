package `in`.androidplay.trackme.services

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.ui.activity.MainActivity
import `in`.androidplay.trackme.util.Constants.ACTION_PAUSE_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import `in`.androidplay.trackme.util.Constants.ACTION_START_OR_RESUME_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_STOP_SERVICE
import `in`.androidplay.trackme.util.Constants.FASTEST_LOCATION_INTERVAL
import `in`.androidplay.trackme.util.Constants.LOCATION_UPDATE_INTERVAL
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_CHANNEL_ID
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_CHANNEL_NAME
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_GROUP_ID
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_GROUP_NAME
import `in`.androidplay.trackme.util.Constants.NOTIFICATION_ID
import `in`.androidplay.trackme.util.Constants.TIMER_UPDATE_INTERVAL
import `in`.androidplay.trackme.util.Helper.logMessage
import `in`.androidplay.trackme.util.PermissionUtil.hasLocationPermission
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 12:02 PM
 */

typealias PolyLine = MutableList<LatLng>
typealias PolyLines = MutableList<PolyLine>

class TrackingService : LifecycleService() {

    private var isFirstRun = true
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val timeRunInSeconds = MutableLiveData<Long>()
    private var isTimerEnabled = false  // setting at 'false' initially
    private var lapTime = 0L            // time to which run time will be added
    private var timeRun = 0L            // total time of run. All lap times added together
    private var timeStarted = 0L        // timestamp when starting the timer.
    private var lastSecondTimestamp = 0L

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this, Observer {
            updateLocationTracking(it)
        })
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        setForegroundService()
                        isFirstRun = false
                    } else {
                        logMessage("Resuming service")
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    logMessage("Pause Service")
                    pauseService()
                }
                ACTION_STOP_SERVICE -> logMessage("Stop Service")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun setForegroundService() {
        startTimer()

        // This starts tracking
        isTracking.postValue(true)

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


    private fun startTimer() {
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true

        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!) {
                lapTime = System.currentTimeMillis() - timeStarted
                timeRunInMillis.postValue(timeRun + lapTime)
                if (timeRunInMillis.value!! >= lastSecondTimestamp + 1000L) {
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)
                    lastSecondTimestamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }


    private fun pauseService() {
        isTracking.postValue(false)
        isTimerEnabled = false
    }


    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            if (hasLocationPermission(this)) {
                val request = LocationRequest()?.apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if (isTracking.value!!) {
                result?.locations?.let { locations ->
                    for (location in locations) {
                        addPathPoint(location)
                        logMessage("NEW LOCATION: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }


    private fun addPathPoint(location: Location) {
        val position = LatLng(location.latitude, location.longitude)
        pathPoints.value?.apply {
            last().add(position)
            pathPoints.postValue(this)
        }
    }


    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))


    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRunInSeconds.postValue(0L)
        timeRunInMillis.postValue(0L)
    }


    companion object {
        val timeRunInMillis = MutableLiveData<Long>()
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<PolyLines>()
    }
}