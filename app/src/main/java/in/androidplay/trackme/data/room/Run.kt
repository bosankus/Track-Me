package `in`.androidplay.trackme.data.room

import android.graphics.Bitmap
import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/30/2020, 11:56 PM
 */

@Entity(tableName = "running_table")
@Stable
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurnt: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}