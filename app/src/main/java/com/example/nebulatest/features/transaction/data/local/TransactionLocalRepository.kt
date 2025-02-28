package com.example.nebulatest.features.transaction.data.local

import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.features.transaction.model.local.TransactionLocalModel
import com.example.nebulatest.features.transaction.model.local.toTransactionLocalModel
import com.example.nebulatest.features.transaction.model.toTransactionEntity
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Factory

@Factory
class TransactionLocalRepository(private val transactionDao: TransactionDao) {

    suspend fun addIncome(income: IncomeModel) {
        transactionDao.insertTransaction(income.toTransactionEntity())
    }

    suspend fun addExpense(expanse: ExpanseModel) {
        transactionDao.insertTransaction(expanse.toTransactionEntity())
    }

    suspend fun getAllTransactions(): List<TransactionLocalModel> {
        return transactionDao.getTransactions().map { it.toTransactionLocalModel() }
    }

    //TODO remove
    private fun fillTransactions() = runBlocking {
        val incomes = listOf(
            IncomeModel(1.0),
            IncomeModel(0.0598),
            IncomeModel(2.0976543)
        )
        val expanses = listOf(
            ExpanseModel(1.074536, TransactionCategory.TAXI),
            ExpanseModel(0.00083265356, TransactionCategory.GROCERIES),
            ExpanseModel(0.4636546, TransactionCategory.OTHER)
        )
        incomes.forEach { addIncome(it) }
        expanses.forEach { addExpense(it) }
    }
}