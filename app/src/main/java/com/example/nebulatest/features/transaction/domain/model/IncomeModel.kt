package com.example.nebulatest.features.transaction.domain.model

import com.example.nebulatest.features.transaction.data.local.model.TransactionEntity

data class IncomeModel(
    val amount: Double,
    val timestamp: Long
)

fun IncomeModel.toTransactionEntity() = TransactionEntity(
    amount = amount,
    category = null,
    transactionType = TransactionType.INCOME,
    timestamp = timestamp
)