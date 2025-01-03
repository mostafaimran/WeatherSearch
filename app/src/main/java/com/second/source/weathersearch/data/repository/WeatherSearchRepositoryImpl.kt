package com.second.source.weathersearch.data.repository

import android.util.Log
import com.second.source.weathersearch.data.dao.AppDao
import com.second.source.weathersearch.data.network.OpenWeatherAPI
import com.second.source.weathersearch.datamodel.exceptions.LocationNotFoundException
import com.second.source.weathersearch.datamodel.exceptions.ServerException
import com.second.source.weathersearch.datamodel.ext.convertNetworkSpecificException
import com.second.source.weathersearch.datamodel.models.WeatherInformation
import com.second.source.weathersearch.datamodel.models.WeatherResponse
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import retrofit2.HttpException
import javax.inject.Inject


class WeatherSearchRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val openWeatherAPI: OpenWeatherAPI
) : WeatherSearchRepository {

    override suspend fun fetchWeatherData(lat: Double, long: Double): WeatherResponse {
        return try {
            val result = openWeatherAPI.getWeather(lat, long)
            if (result.cod == 200)
                result
            else
                throw Exception(ServerException(result.message ?: "something is wrong"))
        } catch (e: Exception) {
            Log.e("WeatherBDRepositoryImpl", "getWeatherData: ", e)
            if (e is HttpException && e.code() == 404) {
                throw LocationNotFoundException()
            } else {
                throw e.convertNetworkSpecificException()
            }
        }
    }

    override suspend fun fetchWeatherData(locationName: String): WeatherResponse {
        return try {
            val result = openWeatherAPI.getWeatherByLocationName(locationName)
            if (result.cod == 200)
                result
            else
                throw Exception(ServerException(result.message ?: "something is wrong"))
        } catch (e: Exception) {
            Log.e("WeatherBDRepositoryImpl", "getWeatherData: ", e)
            if (e is HttpException && e.code() == 404) {
                throw LocationNotFoundException()
            } else {
                throw e.convertNetworkSpecificException()
            }
        }
    }

    override suspend fun getWeatherInformation(): WeatherInformation? {
        return appDao.getWeatherInformation()
    }

    override suspend fun saveWeatherInformation(weatherInformation: WeatherInformation) {
        appDao.insertWeatherInformation(weatherInformation)
    }

}