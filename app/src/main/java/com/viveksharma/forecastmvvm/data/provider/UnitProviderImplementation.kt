package com.viveksharma.forecastmvvm.data.provider

import android.content.Context
import com.viveksharma.forecastmvvm.internal.UnitSystem

class UnitProviderImplementation(context: Context) : UnitProvider, PreferenceProvider(context) {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString("UNIT_SYSTEM", UnitSystem.METRIC.name)

        return UnitSystem.valueOf(selectedName!!)
    }
}