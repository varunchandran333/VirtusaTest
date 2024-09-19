package com.training.programmingtest

import android.app.Application
import com.training.programmingtest.di.networkModule
import com.training.programmingtest.di.repositoryModule
import com.training.programmingtest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}