package com.viveksharma.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.viveksharma.forecastmvvm.R
import com.viveksharma.forecastmvvm.data.network.ConnectivityInterceptorImplementation
import com.viveksharma.forecastmvvm.data.network.WeatherApiService
import com.viveksharma.forecastmvvm.data.network.WeatherNetworkDataSource
import com.viveksharma.forecastmvvm.data.network.WeatherNetworkDataSourceImplementation
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        val apiService = WeatherApiService(ConnectivityInterceptorImplementation(this.requireContext()))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImplementation(apiService)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
            currentText.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeather("Delhi", "en")
        }
    }

}
