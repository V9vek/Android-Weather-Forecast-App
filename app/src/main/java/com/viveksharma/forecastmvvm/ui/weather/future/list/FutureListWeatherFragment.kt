package com.viveksharma.forecastmvvm.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.viveksharma.forecastmvvm.R

class FutureListWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            FutureListWeatherFragment()
    }

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_list_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FutureListWeatherViewModel::class.java)
    }

}
