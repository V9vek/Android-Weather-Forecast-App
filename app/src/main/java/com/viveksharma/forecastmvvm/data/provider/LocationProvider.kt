package com.viveksharma.forecastmvvm.data.provider

import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}