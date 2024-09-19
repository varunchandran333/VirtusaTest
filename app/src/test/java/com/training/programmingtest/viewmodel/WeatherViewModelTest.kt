package com.training.programmingtest.viewmodel

import app.cash.turbine.test
import com.google.gson.Gson
import com.training.programmingtest.data.wrapper.NetworkResult
import com.training.programmingtest.repository.weather.WeatherRepositoryInterface
import com.training.programmingtest.viewmodel.weather.WeatherViewModel
import data.model.Clouds
import data.model.Coord
import data.model.Main
import data.model.Sys
import data.model.Weather
import data.model.WeatherApiResponse
import data.model.Wind

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private val repository: WeatherRepositoryInterface = mockk()

    @Before
    fun setUp() {
        // Set up the test to use a TestCoroutineDispatcher
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `test weatherData flow emits loading and then success`() = runTest {
        val mockData = WeatherApiResponse(
            "", Clouds(0), 0, Coord(0.0, 0.0), 0, 0,
            Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0),
            "",
            Sys("", 0, 0, 0, 0), 0, 0,
            emptyList<Weather>(),
            Wind(0, 0.0, 0.0)
        )
        val expectedResult = NetworkResult.Success(mockData)
        coEvery { repository.getWeather("Texas") } returns flow {
            emit(NetworkResult.Loading)
            emit(expectedResult)
        }

        // Act: Create the viewModel after mocking
        viewModel = WeatherViewModel(repository)
        viewModel.updateCity("Texas")
        // Assert: Use Turbine to test the flow emissions
        viewModel.weatherData.test {
            // First emission should be Loading
            assertEquals(NetworkResult.Loading, awaitItem())

            // Second emission should be Success
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `test weatherData with Lat and Long flow emits loading and then success`() = runTest {
        val mockData = WeatherApiResponse(
            "", Clouds(0), 0, Coord(0.0, 0.0), 0, 0,
            Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0),
            "",
            Sys("", 0, 0, 0, 0), 0, 0,
            emptyList<Weather>(),
            Wind(0, 0.0, 0.0)
        )
        val expectedResult = NetworkResult.Success(mockData)
        coEvery { repository.getWeather(0.0, 0.0) } returns flow {
            emit(NetworkResult.Loading)
            emit(expectedResult)
        }

        // Act: Create the viewModel after mocking
        viewModel = WeatherViewModel(repository)
        viewModel.getWeatherUsingLatAndLong(0.0, 0.0)
        // Assert: Use Turbine to test the flow emissions
        viewModel.weatherData.test {
            // First emission should be Loading
            assertEquals(NetworkResult.Loading, awaitItem())

            // Second emission should be Success
            assertEquals(expectedResult, awaitItem())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}