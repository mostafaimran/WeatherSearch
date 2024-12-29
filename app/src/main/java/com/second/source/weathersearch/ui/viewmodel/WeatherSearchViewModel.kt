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
import com.second.source.weathersearch.datamodel.models.WeatherInformation
import com.second.source.weathersearch.domain.GetLocalWeatherData
import com.second.source.weathersearch.domain.GetWeatherDataByCurrentLocation
import com.second.source.weathersearch.domain.GetWeatherDataByLocationName
import com.second.source.weathersearch.domain.ParamGetWeatherByLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val getWeatherDataByCurrentLocation: GetWeatherDataByCurrentLocation,
    private val getWeatherDataByLocationName: GetWeatherDataByLocationName,
    private val getLocalWeatherData: GetLocalWeatherData
) : BaseViewModel() {

    var weatherScreenState by mutableStateOf(HomeWeatherScreenState())
        private set

    private var controlledRunnerFetchRecords = ControlledRunner<Results<WeatherInformation>>()

    fun updateLocationPermissionGranted() {
        weatherScreenState = weatherScreenState.copy(locationPermissionRequired = false)
    }

    fun updateLocation(latLng: LatLng) {
        weatherScreenState = weatherScreenState.copy(currentLocation = latLng)
    }

    fun handledException() {
        weatherScreenState = weatherScreenState.copy(exception = null)
    }

    fun getLocalWeatherData() {
        uiScope.launch {
            val weatherInformation = getLocalWeatherData(Any())

            weatherScreenState = weatherScreenState.copy(
                weatherInformation = weatherInformation,
                isLoading = false,
            )
        }
    }

    fun getWeatherByLocation(latitude: Double, longitude: Double) {
        uiScope.launch {
            weatherScreenState = weatherScreenState.copy(isLoading = true)

            when (val results = controlledRunnerFetchRecords.cancelPreviousThenRun {
                getWeatherDataByCurrentLocation(
                    ParamGetWeatherByLocation(latitude, longitude)
                )
            }) {
                is Success -> {
                    weatherScreenState = weatherScreenState.copy(
                        weatherInformation = results.data,
                        isLoading = false,
                    )
                }

                is Error -> {
                    weatherScreenState = weatherScreenState.copy(
                        isLoading = false,
                        exception = results.exception,
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
                        weatherInformation = results.data,
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
    val weatherInformation: WeatherInformation? = null,
    val isLoading: Boolean = false,
    val exception: Exception? = null,
    val currentLocation: LatLng? = null,
    val locationPermissionRequired: Boolean = true,
)
