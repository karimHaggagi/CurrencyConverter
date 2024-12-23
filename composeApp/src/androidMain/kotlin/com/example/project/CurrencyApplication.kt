package com.example.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * created by Karim Haggagi on 12/15/24
 **/
class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CurrencyApplication)
        }
    }
}