package com.example.nebulatest.features.exchange.rate.model.remote

data class ExchangeRateDataRemoteModel(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val priceUsd: Double
)