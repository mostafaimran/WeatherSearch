package com.second.source.weathersearch.di

import android.content.Context
import com.google.gson.Gson
import com.second.source.weathersearch.core.JsonConverter
import com.second.source.weathersearch.coreandroid.GsonConverter
import com.second.source.weathersearch.data.repository.WeatherSearchRepositoryImpl
import com.second.source.weathersearch.datamodel.repository.WeatherSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext application: Context): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideNotificationQueueRepository(weatherBDRepositoryImpl: WeatherSearchRepositoryImpl): WeatherSearchRepository =
        weatherBDRepositoryImpl

    @Singleton
    @Provides
    fun provideGsonConverter(): JsonConverter {
        return GsonConverter(Gson())
    }

}