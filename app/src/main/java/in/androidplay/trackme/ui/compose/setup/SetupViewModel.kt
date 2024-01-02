package `in`.androidplay.trackme.ui.compose.setup

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.repository.MainRepository
import `in`.androidplay.trackme.data.room.Run
import `in`.androidplay.trackme.util.Constants
import `in`.androidplay.trackme.util.ResultData
import `in`.androidplay.trackme.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val sharedPreference: SharedPreferences,
    private val repository: MainRepository
) : ViewModel() {

    private val _setupScreenState = MutableStateFlow<ResultData<String>>(ResultData.DoNothing)
    val setupScreenState = _setupScreenState.asStateFlow()

    init {
        insertDummyData() // TODO: Toggle this using remote config
    }

    private fun insertDummyData() {
        viewModelScope.launch { repeat(3){
            repository.insertRun(Run())
        } }
    }

    fun saveSetupData(name: String?, weight: String?) {
        _setupScreenState.update { ResultData.Loading }
        if (name.isNullOrEmpty() || weight.isNullOrEmpty()) {
            _setupScreenState.update { ResultData.Failed(UiText.StringResource(R.string.empty_value_error_txt)) }
        } else {
            sharedPreference.edit()
                .putBoolean(Constants.KEY_FIRST_TIME_TOGGLE, false)
                .putString(Constants.KEY_NAME, name)
                .putFloat(Constants.KEY_WEIGHT, weight.toFloat())
                .apply()
            _setupScreenState.update { ResultData.Success() }
        }
    }
}
