package com.viveksharma.forecastmvvm.data.network.response


import com.google.gson.annotations.SerializedName
import com.viveksharma.forecastmvvm.data.database.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)