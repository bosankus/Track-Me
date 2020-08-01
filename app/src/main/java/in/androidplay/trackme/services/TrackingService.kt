package `in`.androidplay.trackme.services

import android.content.Intent
import androidx.lifecycle.LifecycleService

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 12:02 PM
 */
class TrackingService: LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}