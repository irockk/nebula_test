package com.example.nebulatest.features.exchange.rate.model.remote

data class ExchangeRateResponse(
    val data: ExchangeRateDataRemoteModel,
    val timestamp: Long
)