package com.example.nebulatest.features.exchange.rate.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rate")
data class ExchangeRateEntity(
    @PrimaryKey
    val currency: String = "usd",
    val price: Double,
    val timestamp: Long
)