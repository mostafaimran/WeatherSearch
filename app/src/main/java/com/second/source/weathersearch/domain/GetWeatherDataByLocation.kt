package com.second.source.weathersearch.domain

import com.second.source.weathersearch.core.base.Error
import com.second.source.weathersearch.core.base.Results
import com.second.source.weathersearch.core.base.Success
import com.second.source.weathersearch.core.base.UseCase
import com.second.source.weathersearch.datamodel.models.WeatherResponse
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherDataByLocation @Inject constructor(
    private val weatherSearchRepository: WeatherSearchRepository
) : UseCase<ParamGetWeatherByLocation, Results<WeatherResponse>>() {

    override suspend fun execute(parameters: ParamGetWeatherByLocation): Results<WeatherResponse> =
        withContext(Dispatchers.Default) {
            try {
                val response = weatherSearchRepository.getWeatherData(
                    parameters.latitude,
                    parameters.longitude
                )
                Success(response)
            } catch (e: Exception) {
                Error(e)
            }
        }
}

data class ParamGetWeatherByLocation(
    val latitude: Double,
    val longitude: Double
)