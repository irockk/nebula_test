package com.example.nebulatest.features.exchange.rate.domain

import android.util.Log
import com.example.nebulatest.features.exchange.rate.data.local.ExchangeRateLocalRepository
import com.example.nebulatest.features.exchange.rate.data.remote.ExchangeRateRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class GetExchangeRateUseCase(
    private val exchangeRateRemoteRepository: ExchangeRateRemoteRepository,
    private val exchangeRateLocalRepository: ExchangeRateLocalRepository
) {
    suspend operator fun invoke(): Result<String> {
        return withContext(Dispatchers.IO) {
            val latestExchangeRate = exchangeRateLocalRepository.getLatestExchangeRate()
            if (latestExchangeRate == null || System.currentTimeMillis() > latestExchangeRate.timestamp + 360000) {
                val newExchangeRateResult = exchangeRateRemoteRepository.getExchangeRate()
                val newExchangeRate = newExchangeRateResult.getOrNull()
                if (newExchangeRateResult.isSuccess && newExchangeRate != null) {
                    exchangeRateLocalRepository.setLatestExchangeRate(
                        newExchangeRate.second, newExchangeRate.first
                    )
                }
                //TODO remove
                Log.e("GetExchangeRateUseCase", "new value")
                newExchangeRateResult.map { it.first }
            } else {
                Log.e("GetExchangeRateUseCase", "value from db")
                Result.success(latestExchangeRate.price)
            }
        }
    }
}