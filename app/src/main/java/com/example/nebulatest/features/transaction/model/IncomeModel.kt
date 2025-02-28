package com.example.nebulatest.features.transaction.model

import com.example.nebulatest.features.transaction.model.local.TransactionEntity

data class IncomeModel(
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)

fun IncomeModel.toTransactionEntity() = TransactionEntity(
    amount = amount,
    category = null,
    transactionType = TransactionType.INCOME,
    timestamp = timestamp
)