package com.viveksharma.forecastmvvm.data.network.response


import com.google.gson.annotations.SerializedName
import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)