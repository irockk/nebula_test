package com.example.nebulatest.features.transaction.domain.model

data class TransactionDomainModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategory?,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)