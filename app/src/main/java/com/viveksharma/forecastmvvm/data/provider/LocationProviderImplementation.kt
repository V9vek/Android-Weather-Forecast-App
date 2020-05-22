package com.viveksharma.forecastmvvm.data.provider

import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation

class LocationProviderImplementation : LocationProvider {
    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Delhi"
    }
}