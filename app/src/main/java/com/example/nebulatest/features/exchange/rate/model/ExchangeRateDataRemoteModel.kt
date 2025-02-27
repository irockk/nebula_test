package com.example.nebulatest.features.exchange.rate.model

data class ExchangeRateDataRemoteModel(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val priceUsd: String
)