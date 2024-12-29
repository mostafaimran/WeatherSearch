package com.second.source.weathersearch.datamodel.repository

import com.second.source.weathersearch.datamodel.models.WeatherResponse

interface WeatherSearchRepository {
    suspend fun getWeatherData(lat: Double, long: Double): WeatherResponse
    suspend fun getWeatherData(locationName: String): WeatherResponse
}