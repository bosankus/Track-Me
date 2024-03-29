package `in`.androidplay.trackme.data.viewmodel

import `in`.androidplay.trackme.data.repository.MainRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 8:21 AM
 */
class StatisticsViewModel @ViewModelInject constructor(mainRepository: MainRepository) :
    ViewModel() {

    val totalTimeRun = mainRepository.getTotalRunTimeInMillis()
    val totalDistanceRun = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurnt()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()

    val runSortedByDate = mainRepository.getAllRunsSortedByDate()
}