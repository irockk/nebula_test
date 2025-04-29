package com.example.nebulatest.features.transaction.presentation.model

import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel
import com.example.nebulatest.features.transaction.domain.model.TransactionType

data class TransactionPresentationModel(
    val id: Int,
    val amount: Double,
    val category: TransactionCategoryPresentationModel,
    val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)

fun TransactionDomainModel.toPresentation() = TransactionPresentationModel(
    id = id,
    amount = amount,
    category = category.toPresentation(),
    transactionType = transactionType,
    timestamp = timestamp
)