package `in`.androidplay.trackme.ui.compose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.androidplay.trackme.data.repository.MainRepository
import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.services.SortType
import `in`.androidplay.trackme.util.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: MainRepository) : ViewModel() {

    private val _sortTypeState = MutableStateFlow(SortType.DATE)
    val sortTypeState = _sortTypeState.asStateFlow()

    val sortedRunList: StateFlow<ResultData<List<Run>>> = repository.getRun().map { values ->
        when (_sortTypeState.value) {
            SortType.DATE -> ResultData.Success(values.sortedBy { it.timestamp })
            SortType.RUNNING_TIME -> ResultData.Success(values.sortedBy { it.timeInMillis })
            SortType.AVG_SPEED -> ResultData.Success(values.sortedBy { it.avgSpeedInKMH })
            SortType.DISTANCE -> ResultData.Success(values.sortedBy { it.distanceInMeters })
            SortType.CALORIES -> ResultData.Success(values.sortedBy { it.caloriesBurnt })
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ResultData.Loading
    )

    fun setSortType(type: SortType) {
        _sortTypeState.update { type }
    }
}
