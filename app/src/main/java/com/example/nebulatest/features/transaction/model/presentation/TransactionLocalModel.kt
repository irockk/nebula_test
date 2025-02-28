package com.example.nebulatest.features.transaction.model.presentation

import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.features.transaction.model.TransactionType
import com.example.nebulatest.features.transaction.model.local.TransactionLocalModel

data class TransactionPresentationModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategory?,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)

fun TransactionLocalModel.toTransactionPresentationModel() = TransactionPresentationModel(
    id = id,
    amount = amount,
    category = category,
    transactionType = transactionType,
    timestamp = timestamp
)