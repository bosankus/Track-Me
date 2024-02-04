package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.viewmodel.StatisticsViewModel
import `in`.androidplay.trackme.databinding.FragmentStatisticsBinding
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setObservers()
        // setupBarChart()
    }

    /*private fun setObservers() {
        statisticsViewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = "Total Time: " + getFormattedStopwatchTime(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        })
        statisticsViewModel.totalDistanceRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it / 1000
                val totalDistance = "Total Distance: ${round(km * 10f) / 10f}km"
                binding.tvTotalDistance.text = totalDistance
            }
        })
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalAvgSpeed = "Total Speed: ${round(it * 10f) / 10f}km/h"
                binding.tvTotalAvgSpeed.text = totalAvgSpeed
            }
        })
        statisticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "Total Calories: ${it}kcal"
                binding.tvTotalCalories.text = totalCalories
            }
        })
        statisticsViewModel.runSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeed, "Speed/Time").apply {
                    valueTextColor = Color.BLACK
                    color = ContextCompat.getColor(requireContext(), R.color.grey)
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.invalidate()
            }
        })
    }*/


    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        binding.barChart.axisLeft.apply {
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        binding.barChart.axisRight.apply {
            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
        }
        binding.barChart.apply {
            description.text = "Speed/Time"
            legend.isEnabled = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}