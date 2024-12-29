package com.second.source.weathersearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.second.source.weathersearch.data.DbConstants.DATABASE_VERSION
import com.second.source.weathersearch.data.dao.AppDao
import com.second.source.weathersearch.datamodel.models.WeatherInformation

@Database(
    entities = [WeatherInformation::class],
    version = DATABASE_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppDao(): AppDao
}