package `in`.androidplay.trackme.data.viewmodel

import `in`.androidplay.trackme.data.repository.MainRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 8:21 AM
 */

@HiltViewModel
class StatisticsViewModel @Inject constructor(mainRepository: MainRepository) :
    ViewModel() {

    val totalTimeRun = mainRepository.getTotalRunTimeInMillis()
    val totalDistanceRun = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurnt()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()

    val runSortedByDate = mainRepository.getAllRunsSortedByDate()
}