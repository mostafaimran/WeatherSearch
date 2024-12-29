package com.second.source.weathersearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.second.source.weathersearch.data.DbConstants
import com.second.source.weathersearch.datamodel.models.WeatherInformation

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherInformation(vararg weatherInformation: WeatherInformation)

    @Query("SELECT * FROM ${DbConstants.WEATHER_INFORMATION} LIMIT 1")
    fun getWeatherInformation(): WeatherInformation?
}