package com.example.nebulatest.features.transaction.domain.model

import androidx.room.TypeConverter

enum class TransactionCategory {
    TAXI,
    GROCERIES,
    RESTAURANT,
    ENTERTAINMENT,
    UTILITIES,
    TRAVEL,
    HEALTH
}

class CategoryConverter {
    @TypeConverter
    fun toCategory(value: String) = enumValueOf<TransactionCategory>(value)

    @TypeConverter
    fun fromCategory(value: TransactionCategory) = value.name
}