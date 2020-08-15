package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.viewmodel.StatisticsViewModel
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        statisticsViewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = "Total Time: " + getFormattedStopwatchTime(it)
                tvTotalTime.text = totalTimeRun
            }
        })
        statisticsViewModel.totalDistanceRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000
                val totalDistance = "Total Distance: ${round(km * 10f) / 10f}km"
                tvTotalDistance.text = totalDistance
            }
        })
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalAvgSpeed = "Total Speed: ${round(it * 10f) / 10f}km/h"
                tvTotalAvgSpeed.text = totalAvgSpeed
            }
        })
        statisticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "Total Calories: ${it}kcal"
                tvTotalCalories.text = totalCalories
            }
        })
    }
}