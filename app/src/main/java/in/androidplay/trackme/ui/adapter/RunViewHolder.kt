package `in`.androidplay.trackme.ui.adapter

import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.databinding.LayoutRunListItemBinding
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/9/2020, 10:32 PM
 */
class RunViewHolder(private val binding: LayoutRunListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(runItem: Run) {
        binding.tvDistanceTravelled.text = "Distance: ${runItem.distanceInMeters / 1000f} km"
        binding.tvAvgSpeed.text = "Avg. Speed: ${runItem.avgSpeedInKMH} km/H"
        binding.tvCaloriesBurned.text = "Calories: ${runItem.caloriesBurnt} kcal"
        binding.tvTimeTaken.text = "Duration: " + getFormattedStopwatchTime(runItem.timeInMillis)
        binding.tvDateTimeStamp.text = "Date: " +
                SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(runItem.timestamp)
        Glide.with(itemView.context)
            .load(runItem.img)
            .centerCrop()
            .into(binding.imgMap)
    }
}