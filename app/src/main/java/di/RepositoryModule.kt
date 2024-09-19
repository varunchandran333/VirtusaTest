package com.training.programmingtest.di

import com.training.programmingtest.repository.weather.WeatherDataSource
import com.training.programmingtest.repository.weather.WeatherRepoImpl
import com.training.programmingtest.repository.weather.WeatherRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    factory { WeatherDataSource(get()) }
    factory<WeatherRepositoryInterface> { WeatherRepoImpl(get()) }
}