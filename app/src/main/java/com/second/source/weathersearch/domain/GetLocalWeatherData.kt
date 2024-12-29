package com.second.source.weathersearch.domain

import com.second.source.weathersearch.core.base.UseCase
import com.second.source.weathersearch.datamodel.models.WeatherInformation
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocalWeatherData @Inject constructor(
    private val weatherSearchRepository: WeatherSearchRepository
) : UseCase<Any, WeatherInformation?>() {

    override suspend fun execute(parameters: Any): WeatherInformation? =
        withContext(Dispatchers.IO) {
            weatherSearchRepository.getWeatherInformation()
        }
}