package com.viveksharma.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.viveksharma.forecastmvvm.data.database.CurrentWeatherDao
import com.viveksharma.forecastmvvm.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.viveksharma.forecastmvvm.data.network.WeatherNetworkDataSource
import com.viveksharma.forecastmvvm.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImplementation(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        //Repository do not have lifecycle like fragment/activity, so we don't have to worry about destroy of repository
        //So we are observing forever, even if it destroys
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric)  currentWeatherDao.getWeatherMetric()
            else    currentWeatherDao.getWeatherImperial()
        }

        //out specifies that we can return classes implementing interface, not only directly the interface
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        //We can user Global Scope in repository, as repository don't have lifecycle
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Delhi",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}