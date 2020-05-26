package com.viveksharma.forecastmvvm

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.viveksharma.forecastmvvm.data.database.ForecastDatabase
import com.viveksharma.forecastmvvm.data.network.*
import com.viveksharma.forecastmvvm.data.provider.LocationProvider
import com.viveksharma.forecastmvvm.data.provider.LocationProviderImplementation
import com.viveksharma.forecastmvvm.data.provider.UnitProvider
import com.viveksharma.forecastmvvm.data.provider.UnitProviderImplementation
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.data.repository.ForecastRepositoryImplementation
import com.viveksharma.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.viveksharma.forecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.viveksharma.forecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import java.time.LocalDate

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase.getInstance(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImplementation(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImplementation(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImplementation(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImplementation(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImplementation(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}