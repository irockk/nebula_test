package com.example.nebulatest.features.transaction.domain.model

import androidx.room.TypeConverter

enum class TransactionCategory {
    GROCERIES,
    TAXI,
    ELECTRONICS,
    RESTAURANT,
    OTHER
}

class CategoryConverter {
    @TypeConverter
    fun toCategory(value: String) = enumValueOf<TransactionCategory>(value)

    @TypeConverter
    fun fromCategory(value: TransactionCategory) = value.name
}