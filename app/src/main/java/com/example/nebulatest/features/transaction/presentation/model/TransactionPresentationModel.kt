package com.example.nebulatest.features.transaction.presentation.model

import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionType
import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel

data class TransactionPresentationModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategory?,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)

fun TransactionDomainModel.toPresentation() = TransactionPresentationModel(
    id = id,
    amount = amount,
    category = category,
    transactionType = transactionType,
    timestamp = timestamp
)