package com.viveksharma.forecastmvvm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viveksharma.forecastmvvm.data.database.entity.CurrentWeatherEntry

@Database(entities = [CurrentWeatherEntry::class], version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {

    abstract val currentWeatherDao: CurrentWeatherDao

    companion object {
        private var INSTANCE: ForecastDatabase? = null

        fun getInstance(context: Context): ForecastDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ForecastDatabase::class.java,
                        "forecast_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}