package `in`.androidplay.trackme.util

import java.util.concurrent.TimeUnit

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/3/2020, 7:40 AM
 */
object TimeFormatUtil {

    fun getFormattedStopwatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliSecond = ms

        val hour = TimeUnit.MILLISECONDS.toHours(milliSecond)
        milliSecond -= TimeUnit.HOURS.toMillis(hour)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliSecond)
        milliSecond -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliSecond)

        if (!includeMillis) {
            return "${if (hour < 10) "0" else ""}$hour: " +
                    "${if (minutes < 10) "0" else ""}$minutes: " +
                    "${if (seconds < 10) "0" else ""}$seconds"
        }

        milliSecond -= TimeUnit.SECONDS.toMillis(seconds)
        milliSecond /= 10
        return "${if (hour < 10) "0" else ""}$hour: " +
                "${if (minutes < 10) "0" else ""}$minutes: " +
                "${if (seconds < 10) "0" else ""}$seconds: " +
                "${if (milliSecond < 10) "0" else ""}$milliSecond"

    }
}