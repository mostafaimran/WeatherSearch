package com.second.source.weathersearch.data.network

import com.second.source.weathersearch.BuildConfig
import com.second.source.weathersearch.datamodel.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    @GET(ApiEndPoints.API_GET_WEATHER_DATA)
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): WeatherResponse

    @GET(ApiEndPoints.API_GET_WEATHER_DATA)
    suspend fun getWeatherByLocationName(
        @Query("q") name: String,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): WeatherResponse

}