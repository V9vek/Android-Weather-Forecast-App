package com.viveksharma.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.viveksharma.forecastmvvm.R

class FutureDetailWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            FutureDetailWeatherFragment()
    }

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_detail_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FutureDetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
