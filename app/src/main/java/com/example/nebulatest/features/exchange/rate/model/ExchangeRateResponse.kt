package com.example.nebulatest.features.exchange.rate.model

data class ExchangeRateResponse(
    val data: ExchangeRateDataRemoteModel,
    val timestamp: Long
)