package com.second.source.weathersearch.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.LatLng
import com.second.source.weathersearch.core.base.Error
import com.second.source.weathersearch.core.base.Results
import com.second.source.weathersearch.core.base.Success
import com.second.source.weathersearch.coreandroid.BaseViewModel
import com.second.source.weathersearch.coreandroid.util.ControlledRunner
import com.second.source.weathersearch.datamodel.models.WeatherResponse
import com.second.source.weathersearch.domain.GetWeatherDataByLocation
import com.second.source.weathersearch.domain.GetWeatherDataByLocationName
import com.second.source.weathersearch.domain.ParamGetWeatherByLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val getWeatherDataByLocation: GetWeatherDataByLocation,
    private val getWeatherDataByLocationName: GetWeatherDataByLocationName
) : BaseViewModel() {

    var weatherScreenState by mutableStateOf(HomeWeatherScreenState())
        private set

    private var controlledRunnerFetchRecords = ControlledRunner<Results<WeatherResponse>>()

    fun updateLocationPermissionGranted() {
        weatherScreenState = weatherScreenState.copy(locationPermissionRequired = false)
    }

    fun updateLocation(latLng: LatLng) {
        weatherScreenState = weatherScreenState.copy(currentLocation = latLng)
    }

    fun handledException() {
        weatherScreenState = weatherScreenState.copy(exception = null)
    }

    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        uiScope.launch {
            weatherScreenState = weatherScreenState.copy(isLoading = true)

            when (val results = controlledRunnerFetchRecords.cancelPreviousThenRun {
                getWeatherDataByLocation(
                    ParamGetWeatherByLocation(latitude, longitude)
                )
            }) {
                is Success -> {
                    weatherScreenState = weatherScreenState.copy(
                        weatherResponse = results.data,
                        isLoading = false
                    )
                }

                is Error -> {
                    weatherScreenState = weatherScreenState.copy(
                        isLoading = false,
                        exception = results.exception
                    )
                }
            }
        }
    }

    fun getWeatherByLocationName(locationName: String) {
        uiScope.launch {
            weatherScreenState = weatherScreenState.copy(isLoading = true)

            when (val results = controlledRunnerFetchRecords.cancelPreviousThenRun {
                getWeatherDataByLocationName(locationName)
            }) {
                is Success -> {
                    weatherScreenState = weatherScreenState.copy(
                        weatherResponse = results.data,
                        isLoading = false
                    )
                }

                is Error -> {
                    weatherScreenState = weatherScreenState.copy(
                        isLoading = false,
                        exception = results.exception
                    )
                }
            }
        }
    }
}

data class HomeWeatherScreenState(
    val weatherResponse: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val currentLocation: LatLng? = null,
    val locationPermissionRequired: Boolean = true
)
