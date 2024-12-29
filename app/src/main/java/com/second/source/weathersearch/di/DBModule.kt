package com.second.source.weathersearch.di

import android.content.Context
import androidx.room.Room
import com.second.source.weathersearch.data.DbConstants
import com.second.source.weathersearch.data.dao.AppDao
import com.second.source.weathersearch.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun provideDB(
        @ApplicationContext application: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DbConstants.DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun providesAppDao(parameter: AppDatabase): AppDao {
        return parameter.getAppDao()
    }

}
