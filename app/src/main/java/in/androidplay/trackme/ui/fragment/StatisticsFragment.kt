package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.viewmodel.StatisticsViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

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

            }
        })
        statisticsViewModel.totalDistanceRun.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
        statisticsViewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
        statisticsViewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
    }
}