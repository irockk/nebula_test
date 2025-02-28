package com.example.nebulatest.features.transaction.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nebulatest.features.transaction.model.CategoryConverter
import com.example.nebulatest.features.transaction.model.TransactionTypeConverter
import com.example.nebulatest.features.transaction.model.local.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1
)

@TypeConverters(TransactionTypeConverter::class, CategoryConverter::class)
abstract class TransactionDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}