package com.example.nebulatest.features.exchange.rate.data.local

import com.example.nebulatest.features.exchange.rate.model.local.ExchangeRateEntity
import org.koin.core.annotation.Single

@Single
class ExchangeRateLocalRepository(private val exchangeRateDao: ExchangeRateDao) {
    suspend fun getLatestExchangeRate() = exchangeRateDao.getUsdExchangeRate()

    suspend fun setLatestExchangeRate(timestamp: Long, price: String) {
        exchangeRateDao.setUsdExchangeRate(ExchangeRateEntity(timestamp = timestamp, price = price))
    }
}