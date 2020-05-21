package com.viveksharma.forecastmvvm.data.provider

import com.viveksharma.forecastmvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}