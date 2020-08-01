package `in`.androidplay.trackme.services

import `in`.androidplay.trackme.util.Constants.ACTION_PAUSE_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_START_OR_RESUME_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_STOP_SERVICE
import `in`.androidplay.trackme.util.Helper.logMessage
import android.content.Intent
import androidx.lifecycle.LifecycleService

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 12:02 PM
 */
class TrackingService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> logMessage("Start or resume service")
                ACTION_PAUSE_SERVICE -> logMessage("Pause Service")
                ACTION_STOP_SERVICE -> logMessage("Stop Service")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}