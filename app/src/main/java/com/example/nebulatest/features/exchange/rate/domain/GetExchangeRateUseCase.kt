package com.example.nebulatest.features.exchange.rate.domain

import com.example.nebulatest.features.exchange.rate.data.local.ExchangeRateLocalRepository
import com.example.nebulatest.features.exchange.rate.data.remote.ExchangeRateRemoteRepository
import org.koin.core.annotation.Factory

@Factory
class GetExchangeRateUseCase(
    private val exchangeRateRemoteRepository: ExchangeRateRemoteRepository,
    private val exchangeRateLocalRepository: ExchangeRateLocalRepository
) {
    suspend operator fun invoke(): Result<Double> {
        val latestExchangeRate = exchangeRateLocalRepository.getLatestExchangeRate()
        return if (latestExchangeRate == null || System.currentTimeMillis() > latestExchangeRate.timestamp + 3600000) {
            val newExchangeRateResult = exchangeRateRemoteRepository.getExchangeRate()
            val newExchangeRate = newExchangeRateResult.getOrNull()
            if (newExchangeRateResult.isSuccess && newExchangeRate != null) {
                exchangeRateLocalRepository.setLatestExchangeRate(
                    newExchangeRate.second, newExchangeRate.first
                )
            }
            newExchangeRateResult.map { it.first }
        } else {
            Result.success(latestExchangeRate.price)
        }
    }
}