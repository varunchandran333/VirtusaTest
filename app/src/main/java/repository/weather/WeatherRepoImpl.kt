package com.training.programmingtest.repository.weather

import com.training.programmingtest.data.wrapper.NetworkResult
import data.model.WeatherApiResponse
import kotlinx.coroutines.flow.Flow

class WeatherRepoImpl(private val dataSource: WeatherDataSource) : WeatherRepositoryInterface {
    override fun getWeather(city: String): Flow<NetworkResult<WeatherApiResponse>> =
        dataSource.getWeather(city)
}