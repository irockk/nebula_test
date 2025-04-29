package com.example.nebulatest.features.transaction.domain.model

import com.example.nebulatest.features.transaction.data.local.model.TransactionEntity

data class ExpenseModel(
    val amount: Double,
    val category: TransactionCategory,
    val timestamp: Long
)

fun ExpenseModel.toTransactionEntity() = TransactionEntity(
    amount = amount * -1,
    category = category,
    transactionType = TransactionType.EXPENSE,
    timestamp = timestamp
)
