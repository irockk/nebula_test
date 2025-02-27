package com.example.nebulatest.features.exchange.rate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nebulatest.features.exchange.rate.model.local.ExchangeRateEntity

@Database(
    entities = [ExchangeRateEntity::class],
    version = 1
)

abstract class ExchangeRateDataBase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao
}