package com.viveksharma.forecastmvvm

import android.app.Application
import com.viveksharma.forecastmvvm.data.database.ForecastDatabase
import com.viveksharma.forecastmvvm.data.network.*
import com.viveksharma.forecastmvvm.data.repository.ForecastRepository
import com.viveksharma.forecastmvvm.data.repository.ForecastRepositoryImplementation
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase.getInstance(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImplementation(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImplementation(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImplementation(instance(), instance()) }
    }
}