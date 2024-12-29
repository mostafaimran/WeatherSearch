package com.second.source.weathersearch.datamodel.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.second.source.weathersearch.data.DbConstants

@Entity(tableName = DbConstants.WEATHER_INFORMATION)
data class WeatherInformation(
    @PrimaryKey @ColumnInfo(name = DbConstants.CITY_NAME) val cityName: String,
    @ColumnInfo(name = DbConstants.TEMPERATURE) val temperature: Double,
    @ColumnInfo(name = DbConstants.DESCRIPTION) val description: String,
    @ColumnInfo(name = DbConstants.WEATHER_ICON) val weatherIcon: String?,
    @ColumnInfo(name = DbConstants.PRESSURE) val pressure: Int,
    @ColumnInfo(name = DbConstants.FEELS_LIKE) val feelsLike: Double,
    @ColumnInfo(name = DbConstants.HUMIDITY) val humidity: Int
)