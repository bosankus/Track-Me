package `in`.androidplay.trackme.data.viewmodel

import `in`.androidplay.trackme.data.repository.MainRepository
import `in`.androidplay.trackme.room.Run
import `in`.androidplay.trackme.services.SortType
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 8:19 AM
 */
class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val runSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runSortedByTime = mainRepository.getAllRunsSortedByTimeInMillis()
    private val runSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runSortedByCalories = mainRepository.getAllRunsSortedByCaloriesBurnt()
    private val runSortedBySpeed = mainRepository.getAllRunsSortedByAvgSpeed()
    private var sortType = SortType.DATE

    val run = MediatorLiveData<List<Run>>()

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

    fun sortType(sortType: SortType) = when (sortType) {
        SortType.DATE -> runSortedByDate.value?.let { run.value = it }
        SortType.RUNNING_TIME -> runSortedByTime.value?.let { run.value = it }
        SortType.DISTANCE -> runSortedByDistance.value?.let { run.value = it }
        SortType.CALORIES -> runSortedByCalories.value?.let { run.value = it }
        SortType.AVG_SPEED -> runSortedBySpeed.value?.let { run.value = it }
    }.also {
        this.sortType = sortType
    }

    init {
        run.addSource(runSortedByDate) {
            if (sortType == SortType.DATE) {
                it?.let { run.value = it }
            }
        }
        run.addSource(runSortedByCalories) {
            if (sortType == SortType.CALORIES) {
                it?.let { run.value = it }
            }
        }
        run.addSource(runSortedByDistance) {
            if (sortType == SortType.DISTANCE) {
                it?.let { run.value = it }
            }
        }
        run.addSource(runSortedBySpeed) {
            if (sortType == SortType.AVG_SPEED) {
                it?.let { run.value = it }
            }
        }
        run.addSource(runSortedByTime) {
            if (sortType == SortType.RUNNING_TIME) {
                it?.let { run.value = it }
            }
        }
    }
}