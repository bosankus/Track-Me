package `in`.androidplay.trackme.util

sealed class ResultData<out T> {
    data object DoNothing : ResultData<Nothing>()
    data object Loading : ResultData<Nothing>()
    data class Success<out T>(val data: T? = null) : ResultData<T>()
    data class Failed(val message: UiText? = null) : ResultData<Nothing>()
}

/*
sealed interface WeatherUiState {
    data object Loading: WeatherUiState
    data class Success(val data: String): WeatherUiState
    data class Failed(val message: UiText?): WeatherUiState
}*/
