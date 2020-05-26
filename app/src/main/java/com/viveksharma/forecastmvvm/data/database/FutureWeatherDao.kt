package com.viveksharma.forecastmvvm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viveksharma.forecastmvvm.data.database.entity.FutureWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.detail.ImperialDetailFutureWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.detail.MetricDetailFutureWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.list.ImperialSimpleFutureWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.list.MetricSimpleFutureWeatherEntry
import java.time.LocalDate

@Dao
interface FutureWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather_table where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather_table where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather_table where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather_table where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate): Int

    //Detail
    @Query("select * from future_weather_table where date(date) = date(:date)")
    fun getDetailedWeatherByDateMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>

    @Query("select * from future_weather_table where date(date) = date(:date)")
    fun getDetailedWeatherByDateImperial(date: LocalDate): LiveData<ImperialDetailFutureWeatherEntry>
}