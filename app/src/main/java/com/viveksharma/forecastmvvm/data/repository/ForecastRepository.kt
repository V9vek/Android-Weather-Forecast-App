package com.viveksharma.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.viveksharma.forecastmvvm.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}