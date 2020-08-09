package `in`.androidplay.trackme.ui.adapter

import `in`.androidplay.trackme.room.Run
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
        itemView.tvDistanceTravelled.text = "${runItem.distanceInMeters / 1000f} km"
        itemView.tvAvgSpeed.text = "${runItem.avgSpeedInKMH} km/H"
        itemView.tvCaloriesBurned.text = "${runItem.caloriesBurnt} kcal"
        itemView.tvTimeTaken.text = getFormattedStopwatchTime(runItem.timeInMillis)
        itemView.tvDateTimeStamp.text =
            SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(runItem.timestamp)
        Glide.with(itemView.context)
            .load(runItem.img)
            .centerCrop()
            .into(itemView.imgMap)
    }
}