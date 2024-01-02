package `in`.androidplay.trackme.data.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.androidplay.trackme.data.repository.MainRepository
import javax.inject.Inject

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 8:19 AM
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

}