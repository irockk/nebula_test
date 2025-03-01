package com.example.nebulatest.di

import android.app.Application
import androidx.room.Room
import com.example.nebulatest.core.Constants.BASE_EXCHANGE_RATE_URL
import com.example.nebulatest.features.exchange.rate.data.local.ExchangeRateDao
import com.example.nebulatest.features.exchange.rate.data.local.ExchangeRateDataBase
import com.example.nebulatest.features.exchange.rate.data.remote.ExchangeRateRemoteRepository
import com.example.nebulatest.features.exchange.rate.data.remote.ExchangeRateService
import com.example.nebulatest.features.transaction.data.local.TransactionDao
import com.example.nebulatest.features.transaction.data.local.TransactionDataBase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_EXCHANGE_RATE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    fun provideNetworkApi(retrofit: Retrofit) = retrofit.create(ExchangeRateService::class.java)

    factory { ExchangeRateRemoteRepository(exchangeRateService = provideNetworkApi(provideRetrofit())) }
}

val exchangeRateDbModule = module {
    fun provideDataBase(application: Application): ExchangeRateDataBase =
        Room.databaseBuilder(application, ExchangeRateDataBase::class.java, "exchange_rate")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(exchangeDataBase: ExchangeRateDataBase): ExchangeRateDao =
        exchangeDataBase.exchangeRateDao()
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}

val transactionDbModule = module {
    fun provideDataBase(application: Application): TransactionDataBase =
        Room.databaseBuilder(application, TransactionDataBase::class.java, "transaction")
            .fallbackToDestructiveMigration()
            .build()

    fun provideDao(transactionDataBase: TransactionDataBase): TransactionDao =
        transactionDataBase.transactionDao()
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}