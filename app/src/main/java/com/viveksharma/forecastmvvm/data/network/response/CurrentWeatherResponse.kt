package com.viveksharma.forecastmvvm.data.network.response

import com.google.gson.annotations.SerializedName
import com.viveksharma.forecastmvvm.data.database.entity.CurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation


data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)