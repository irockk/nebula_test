package com.example.nebulatest.features.exchange.rate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nebulatest.features.exchange.rate.data.local.model.ExchangeRateEntity

@Dao
interface ExchangeRateDao {

    @Query("SELECT * FROM exchange_rate WHERE currency = 'usd'")
    suspend fun getUsdExchangeRate(): ExchangeRateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUsdExchangeRate(exchangeRateEntity: ExchangeRateEntity)
}