package com.training.programmingtest.repository.weather

import com.training.programmingtest.data.remote.network.weather.WeatherApi
import com.training.programmingtest.data.wrapper.NetworkResult
import com.training.programmingtest.data.wrapper.handleApi
import data.model.WeatherApiResponse
import kotlinx.coroutines.flow.Flow

class WeatherDataSource(
    private val apiService: WeatherApi
) {
    fun getWeather(city: String):
            Flow<NetworkResult<WeatherApiResponse>> = handleApi {
        apiService.getWeather(city = city)
    }
}