package com.training.programmingtest.viewmodel.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.programmingtest.data.wrapper.NetworkResult
import com.training.programmingtest.repository.weather.WeatherRepositoryInterface
import data.model.WeatherApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class WeatherViewModel(private val repositoryInterface: WeatherRepositoryInterface) : ViewModel() {
    var weatherData: MutableStateFlow<NetworkResult<WeatherApiResponse>> =
        MutableStateFlow(NetworkResult.Loading)
    private var city: MutableStateFlow<String> = MutableStateFlow("")
    private fun getWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (city.value.isNotEmpty()) {
                repositoryInterface.getWeather(city.value).distinctUntilChanged()
                    .collect { response ->
                        weatherData.value = response
                    }
            }
        }
    }

    fun updateCity(data: String) {
        city.value = data
        getWeatherData()
    }
}