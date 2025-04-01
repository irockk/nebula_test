package com.example.nebulatest.features.balance.domain

import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import org.koin.core.annotation.Factory

@Factory
class BalanceUseCase(private val balanceLocalDataSource: BalanceLocalDataSource) {

    fun observeBalance() = balanceLocalDataSource.observeBalance()

    suspend fun updateBalance(newTransactionAmount: Double) =
        balanceLocalDataSource.updateBalance(newTransactionAmount)
}