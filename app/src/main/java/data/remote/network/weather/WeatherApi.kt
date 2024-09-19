package com.training.programmingtest.data.remote.network.weather

import com.training.programmingtest.utils.APP_ID
import data.model.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(value = "data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String = APP_ID,
        @Query("units") units: String = "imperial"
    ): Response<WeatherApiResponse>

    @GET(value = "data/2.5/weather")
    suspend fun getWeatherLatAndLog(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = APP_ID,
        @Query("units") units: String = "imperial"
    ): Response<WeatherApiResponse>
}