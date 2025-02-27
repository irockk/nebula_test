package com.example.nebulatest.features.exchange.rate.data

import org.koin.core.annotation.Single

@Single
class ExchangeRateRepository(private val exchangeRateService: ExchangeRateService) {

    suspend fun getExchangeRate(): Result<String> {
        return try {
            val response = exchangeRateService.getExchangeRate()
            if (response.isSuccessful) {
                Result.success(response.body()?.data?.priceUsd ?: throw Exception("No data found"))
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}