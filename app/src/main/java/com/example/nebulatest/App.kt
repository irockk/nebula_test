package com.example.nebulatest

import android.app.Application
import com.example.nebulatest.di.exchangeRateDbModule
import com.example.nebulatest.di.networkModule
import com.example.nebulatest.di.transactionDbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(AppAnnotationModule().module + networkModule + exchangeRateDbModule + transactionDbModule)
        }
    }
}