package com.training.programmingtest.di

import com.training.testapp.repository.weather.WeatherDataSource
import com.training.programmingtest.repository.weather.WeatherRepoImpl
import com.training.testapp.repository.weather.WeatherRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    factory { WeatherDataSource(get()) }
    factory<WeatherRepositoryInterface> { WeatherRepoImpl(get()) }
}