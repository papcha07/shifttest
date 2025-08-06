package com.example.testovoe

import android.app.Application
import com.example.testovoe.di.appModule
import com.example.testovoe.di.dataModule
import com.example.testovoe.di.domainModule
import com.example.testovoe.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(listOf(appModule,dataModule, domainModule, viewModelModule))
        }
    }
}