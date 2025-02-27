package com.example.nebulatest.features.exchange.rate.data.remote

import com.example.nebulatest.features.exchange.rate.model.remote.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRateService {
    @GET("assets/bitcoin")
    suspend fun getExchangeRate(): Response<ExchangeRateResponse>
}