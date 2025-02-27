package com.example.nebulatest.di

import com.example.nebulatest.features.exchange.rate.data.ExchangeRateRepository
import com.example.nebulatest.features.exchange.rate.data.ExchangeRateService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.coincap.io/v2/"

val networkModule = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    fun provideNetworkApi(retrofit: Retrofit) = retrofit.create(ExchangeRateService::class.java)

    factory { ExchangeRateRepository(exchangeRateService = provideNetworkApi(provideRetrofit())) }
}