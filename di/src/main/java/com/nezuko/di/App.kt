package com.nezuko.di

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.binds
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            module {
                single { this@App } binds arrayOf(Context::class, Application::class)
            }
            androidLogger(Level.DEBUG)
            modules(listOf(dataModule, appModule, domainModule))
            androidContext(applicationContext)
        }
    }
}