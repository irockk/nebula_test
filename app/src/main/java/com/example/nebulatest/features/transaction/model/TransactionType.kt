package com.example.nebulatest.features.transaction.model

import androidx.room.TypeConverter

enum class TransactionType {
    INCOME,
    EXPENSE
}

class TransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(value: String) = enumValueOf<TransactionType>(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType) = value.name
}