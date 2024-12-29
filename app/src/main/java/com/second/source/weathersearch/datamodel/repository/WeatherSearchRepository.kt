package com.second.source.weathersearch.datamodel.repository

import com.second.source.weathersearch.datamodel.models.WeatherInformation
import com.second.source.weathersearch.datamodel.models.WeatherResponse

interface WeatherSearchRepository {
    suspend fun fetchWeatherData(lat: Double, long: Double): WeatherResponse
    suspend fun fetchWeatherData(locationName: String): WeatherResponse
    suspend fun getWeatherInformation(): WeatherInformation?
    suspend fun saveWeatherInformation(weatherInformation: WeatherInformation)
}