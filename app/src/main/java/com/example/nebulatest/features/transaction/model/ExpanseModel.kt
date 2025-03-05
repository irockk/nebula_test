package com.example.nebulatest.features.transaction.model

import com.example.nebulatest.features.transaction.model.local.TransactionEntity

data class ExpanseModel(
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: Long
)

fun ExpanseModel.toTransactionEntity() = TransactionEntity(
    amount = amount * -1,
    category = category,
    transactionType = TransactionType.EXPENSE,
    timestamp = timestamp
)
