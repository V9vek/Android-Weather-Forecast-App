package com.viveksharma.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation
import com.viveksharma.forecastmvvm.data.database.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import java.time.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>

    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}