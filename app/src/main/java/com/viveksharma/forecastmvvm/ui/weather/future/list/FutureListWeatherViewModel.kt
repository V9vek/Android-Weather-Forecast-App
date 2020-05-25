package com.viveksharma.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.viveksharma.forecastmvvm.data.provider.UnitProvider
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.internal.lazyDeferred
import com.viveksharma.forecastmvvm.ui.base.WeatherViewModel
import java.time.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetric)
    }

}
