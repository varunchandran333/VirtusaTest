package com.training.programmingtest.di

import com.training.programmingtest.viewmodel.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        WeatherViewModel(get())
    }
}