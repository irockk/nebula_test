package com.example.nebulatest.features.transaction.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.features.transaction.model.local.TransactionEntity
import com.example.nebulatest.features.transaction.model.local.TransactionLocalModel
import com.example.nebulatest.features.transaction.model.local.toTransactionLocalModel
import com.example.nebulatest.features.transaction.model.toTransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class TransactionLocalRepository(private val transactionDao: TransactionDao) {

    private var pagingSource: PagingSource<Int, TransactionEntity>? = null

    suspend fun addIncome(income: IncomeModel) {
        transactionDao.insertTransaction(income.toTransactionEntity())
        pagingSource?.invalidate()
    }

    suspend fun addExpense(expanse: ExpanseModel) {
        transactionDao.insertTransaction(expanse.toTransactionEntity())
        pagingSource?.invalidate()
    }

    fun getTransactionsPaged(): Flow<PagingData<TransactionLocalModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { transactionDao.getTransactionsPaged() }
        ).flow.map { pagingData ->
            pagingData.map { it.toTransactionLocalModel() }
        }.flowOn(Dispatchers.IO)
    }
}