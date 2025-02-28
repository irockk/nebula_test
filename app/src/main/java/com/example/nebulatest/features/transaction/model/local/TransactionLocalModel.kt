package com.example.nebulatest.features.transaction.model.local

import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.features.transaction.model.TransactionType

data class TransactionLocalModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategory?,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)