package com.training.programmingtest.repository.weather

import com.training.programmingtest.data.wrapper.NetworkResult
import data.model.WeatherApiResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {
    fun getWeather(city: String):
            Flow<NetworkResult<WeatherApiResponse>>

    fun getWeather(lat: Double, log: Double):
            Flow<NetworkResult<WeatherApiResponse>>
}