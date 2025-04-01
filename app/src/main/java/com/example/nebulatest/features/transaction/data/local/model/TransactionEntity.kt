package com.example.nebulatest.features.transaction.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nebulatest.features.transaction.domain.model.CategoryConverter
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionType
import com.example.nebulatest.features.transaction.domain.model.TransactionTypeConverter

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    @TypeConverters(CategoryConverter::class) val category: TransactionCategory?,
    @TypeConverters(TransactionTypeConverter::class) val transactionType: TransactionType,
    val timestamp: Long = System.currentTimeMillis()
)

fun TransactionEntity.toTransactionLocalModel() = TransactionLocalModel(
    id = id,
    amount = amount,
    category = category,
    transactionType = transactionType,
    timestamp = timestamp
)
