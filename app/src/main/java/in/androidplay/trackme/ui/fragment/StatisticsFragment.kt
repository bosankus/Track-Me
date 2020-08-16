package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.viewmodel.StatisticsViewModel
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setupBarChart()
    }

    private fun setObservers() {
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
        statisticsViewModel.runSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeed, "Speed/Time").apply {
                    valueTextColor = Color.BLACK
                    color = ContextCompat.getColor(requireContext(), R.color.grey)
                }
                barChart.data = BarData(barDataSet)
                barChart.invalidate()
            }
        })
    }


    private fun setupBarChart() {
        barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        barChart.axisLeft.apply {
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        barChart.axisRight.apply {
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        barChart.apply {
            description.text = "Speed/Time"
            legend.isEnabled = false
        }
    }
}