package com.training.programmingtest.viewmodel.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.programmingtest.data.wrapper.NetworkResult
import com.training.programmingtest.repository.weather.WeatherRepositoryInterface
import data.model.WeatherApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WeatherViewModel(private val repositoryInterface: WeatherRepositoryInterface) : ViewModel() {
    var weatherData: StateFlow<NetworkResult<WeatherApiResponse>> =
        MutableStateFlow(NetworkResult.Loading)
    private var city: MutableStateFlow<String> = MutableStateFlow("")

    private fun getWeatherData() {
        weatherData =
            repositoryInterface.getWeather(city.value)
                .stateIn(
                    scope = viewModelScope,
                    initialValue = NetworkResult.Loading,
                    started = SharingStarted.WhileSubscribed(5000L)
                )
    }

    fun getWeatherUsingLatAndLong(lat: Double, long: Double) {
        weatherData =
            repositoryInterface.getWeather(lat, long)
                .stateIn(
                    scope = viewModelScope,
                    initialValue = NetworkResult.Loading,
                    started = SharingStarted.WhileSubscribed(5000L)
                )
    }

    fun updateCity(data: String) {
        city.value = data
        getWeatherData()
    }
}