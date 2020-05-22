package com.viveksharma.forecastmvvm

import android.app.Application
import com.viveksharma.forecastmvvm.data.database.ForecastDatabase
import com.viveksharma.forecastmvvm.data.network.*
import com.viveksharma.forecastmvvm.data.provider.LocationProvider
import com.viveksharma.forecastmvvm.data.provider.LocationProviderImplementation
import com.viveksharma.forecastmvvm.data.provider.UnitProvider
import com.viveksharma.forecastmvvm.data.provider.UnitProviderImplementation
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.data.repository.ForecastRepositoryImplementation
import com.viveksharma.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase.getInstance(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImplementation(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImplementation(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImplementation() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImplementation(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImplementation(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}