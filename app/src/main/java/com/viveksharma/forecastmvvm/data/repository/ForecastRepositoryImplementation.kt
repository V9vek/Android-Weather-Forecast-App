package com.viveksharma.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.viveksharma.forecastmvvm.data.database.CurrentWeatherDao
import com.viveksharma.forecastmvvm.data.database.FutureWeatherDao
import com.viveksharma.forecastmvvm.data.database.WeatherLocationDao
import com.viveksharma.forecastmvvm.data.database.entity.WeatherLocation
import com.viveksharma.forecastmvvm.data.database.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.database.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.viveksharma.forecastmvvm.data.network.FORECAST_DAYS_COUNT
import com.viveksharma.forecastmvvm.data.network.WeatherNetworkDataSource
import com.viveksharma.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.viveksharma.forecastmvvm.data.network.response.FutureWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImplementation(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: com.viveksharma.forecastmvvm.data.provider.LocationProvider
) : ForecastRepository {

    init {
        //Repository do not have lifecycle like fragment/activity, so we don't have to worry about destroy of repository
        //So we are observing forever, even if it destroys
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever {
                persistFetchedCurrentWeather(it)
            }

            downloadedFutureWeather.observeForever {
                persistFetchedFutureWeather(it)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }

        //out specifies that we can return classes implementing interface, not only directly the interface
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate, metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        //We can user Global Scope in repository, as repository don't have lifecycle
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        //if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if (isFetchFutureNeeded())
            fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }
}