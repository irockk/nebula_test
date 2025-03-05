package com.example.nebulatest.features.exchange.rate.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class ExchangeRateRemoteRepository(private val exchangeRateService: ExchangeRateService) {

    suspend fun getExchangeRate(): Result<Pair<Double, Long>> {
        return try {
            withContext(Dispatchers.IO) {
                val response = exchangeRateService.getExchangeRate()
                if (response.isSuccessful) {
                    Result.success(response.body()?.let { it.data.priceUsd to it.timestamp }
                        ?: throw Exception("No data found"))
                } else {
                    Result.failure(Exception(response.message()))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}