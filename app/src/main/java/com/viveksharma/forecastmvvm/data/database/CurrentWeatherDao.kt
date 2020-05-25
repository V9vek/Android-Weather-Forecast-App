package com.viveksharma.forecastmvvm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viveksharma.forecastmvvm.data.database.entity.CURRENT_WEATHER_ID
import com.viveksharma.forecastmvvm.data.database.entity.CurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.current.ImperialCurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    /**
     * upsert() will insert data/entity of type CurrentWeatherEntry and if it is already present then update the database
     * as there is always only 1 row with same ID = 0
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather_table WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather_table WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}