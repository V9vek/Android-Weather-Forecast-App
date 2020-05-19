package com.viveksharma.forecastmvvm.data.network.response

import com.google.gson.annotations.SerializedName
import com.viveksharma.forecastmvvm.data.database.entity.CurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.entity.Location


data class CurrentWeatherResponse(
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)