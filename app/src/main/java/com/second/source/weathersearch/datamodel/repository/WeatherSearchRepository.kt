package com.second.source.weathersearch.datamodel.repository

import com.second.source.weathersearch.datamodel.models.WeatherResponse
import com.second.source.weathersearch.datamodel.models.Zila

interface WeatherSearchRepository {
    suspend fun getWeatherData(lat: Double, long: Double): WeatherResponse
}