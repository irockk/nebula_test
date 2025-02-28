package com.example.nebulatest.features.transaction.data.local

import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.features.transaction.model.local.TransactionLocalModel
import com.example.nebulatest.features.transaction.model.local.toTransactionLocalModel
import com.example.nebulatest.features.transaction.model.toTransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class TransactionLocalRepository(private val transactionDao: TransactionDao) {

    suspend fun addIncome(income: IncomeModel) {
        withContext(Dispatchers.IO) {
            transactionDao.insertTransaction(income.toTransactionEntity())
        }
    }

    suspend fun addExpense(expanse: ExpanseModel) {
        withContext(Dispatchers.IO) {
            transactionDao.insertTransaction(expanse.toTransactionEntity())
        }
    }

    suspend fun getAllTransactions(): List<TransactionLocalModel> {
        return withContext(Dispatchers.IO) {
            transactionDao.getTransactions().map { it.toTransactionLocalModel() }
        }
    }
}