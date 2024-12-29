package com.second.source.weathersearch.domain

import com.second.source.weathersearch.core.base.Error
import com.second.source.weathersearch.core.base.Results
import com.second.source.weathersearch.core.base.Success
import com.second.source.weathersearch.core.base.UseCase
import com.second.source.weathersearch.datamodel.models.WeatherInformation
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherDataByCurrentLocation @Inject constructor(
    private val weatherSearchRepository: WeatherSearchRepository
) : UseCase<ParamGetWeatherByLocation, Results<WeatherInformation>>() {

    override suspend fun execute(parameters: ParamGetWeatherByLocation): Results<WeatherInformation> =
        withContext(Dispatchers.IO) {
            try {
                val response = weatherSearchRepository.fetchWeatherData(
                    parameters.latitude,
                    parameters.longitude
                )
                val weatherInformation = WeatherInformation(
                    cityName = response.name,
                    temperature = response.main.temp,
                    description = response.weather.firstOrNull()?.description ?: "",
                    weatherIcon = response.weather.firstOrNull()?.icon ?: "",
                    pressure = response.main.pressure,
                    feelsLike = response.main.feelsLike,
                    humidity = response.main.humidity
                )

                // save to db
                weatherSearchRepository.saveWeatherInformation(weatherInformation)

                Success(weatherInformation)
            } catch (e: Exception) {
                Error(e)
            }
        }
}

data class ParamGetWeatherByLocation(
    val latitude: Double,
    val longitude: Double
)