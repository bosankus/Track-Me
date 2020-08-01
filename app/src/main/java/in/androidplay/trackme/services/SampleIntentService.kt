package `in`.androidplay.trackme.services

import `in`.androidplay.trackme.util.Helper.logMessage
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 2:14 PM
 */
class SampleIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        for (i in 1..60) {
            logMessage("Task: $i")
            Thread.sleep(1000)
        }
    }

    companion object {
        private const val JOB_ID = 111

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, SampleIntentService::class.java, JOB_ID, work)
        }
    }
}