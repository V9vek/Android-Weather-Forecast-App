package com.viveksharma.forecastmvvm.ui.base

import androidx.lifecycle.ViewModel
import com.viveksharma.forecastmvvm.data.provider.UnitProvider
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.internal.UnitSystem
import com.viveksharma.forecastmvvm.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel(){

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}