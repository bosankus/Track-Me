package `in`.androidplay.trackme.viewmodel

import `in`.androidplay.trackme.repository.MainRepository
import `in`.androidplay.trackme.room.Run
import androidx.hilt.lifecycle.ViewModelInject
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

    fun inserRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}