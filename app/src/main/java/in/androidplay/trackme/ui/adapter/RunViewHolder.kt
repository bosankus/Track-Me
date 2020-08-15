package `in`.androidplay.trackme.ui.adapter

import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_run_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/9/2020, 10:32 PM
 */
class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun bind(runItem: Run) {
        itemView.tvDistanceTravelled.text = "Distance: ${runItem.distanceInMeters / 1000f} km"
        itemView.tvAvgSpeed.text = "Avg. Speed: ${runItem.avgSpeedInKMH} km/H"
        itemView.tvCaloriesBurned.text = "Calories: ${runItem.caloriesBurnt} kcal"
        itemView.tvTimeTaken.text = "Duration: " + getFormattedStopwatchTime(runItem.timeInMillis)
        itemView.tvDateTimeStamp.text = "Date: " +
            SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(runItem.timestamp)
        Glide.with(itemView.context)
            .load(runItem.img)
            .centerCrop()
            .into(itemView.imgMap)
    }
}