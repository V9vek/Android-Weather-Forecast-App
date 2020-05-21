package com.viveksharma.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.viveksharma.forecastmvvm.data.provider.UnitProvider
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.internal.UnitSystem
import com.viveksharma.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean get() = unitSystem == UnitSystem.METRIC

    /*  getting current weather from repository co-routine call in a lazy manner.
        lazy means when weather will be called from View, then it will be check if it is initialized
        and if it is not, it will get initialized, and never after
    */
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
