package com.example.nebulatest.features.transaction.data.local.model

import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionType
import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel

data class TransactionLocalModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategory?,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)

fun TransactionLocalModel.toDomain() = TransactionDomainModel(
    id = id,
    amount = amount,
    category = category,
    transactionType = transactionType,
    timestamp = timestamp
)