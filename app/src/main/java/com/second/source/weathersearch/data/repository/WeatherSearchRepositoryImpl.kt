package com.second.source.weathersearch.data.repository

import android.util.Log
import com.second.source.weathersearch.data.network.OpenWeatherAPI
import com.second.source.weathersearch.datamodel.exceptions.ServerException
import com.second.source.weathersearch.datamodel.ext.convertNetworkSpecificException
import com.second.source.weathersearch.datamodel.models.WeatherResponse
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import javax.inject.Inject


class WeatherSearchRepositoryImpl @Inject constructor(
    private val openWeatherAPI: OpenWeatherAPI
) : WeatherSearchRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): WeatherResponse {
        return try {
            val result = openWeatherAPI.getWeather(lat, long)
            if (result.cod == 200)
                result
            else
                throw Exception(ServerException(result.message ?: "something is wrong"))
        } catch (e: Exception) {
            Log.e("WeatherBDRepositoryImpl", "getWeatherData: ", e)
            throw e.convertNetworkSpecificException()
        }
    }

}