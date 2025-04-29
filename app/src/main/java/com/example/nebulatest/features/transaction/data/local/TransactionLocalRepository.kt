package com.example.nebulatest.features.transaction.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.IncomeModel
import com.example.nebulatest.features.transaction.domain.model.toTransactionEntity
import com.example.nebulatest.features.transaction.data.local.model.TransactionEntity
import com.example.nebulatest.features.transaction.data.local.model.toDomain
import com.example.nebulatest.features.transaction.data.local.model.toTransactionLocalModel
import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel
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

    suspend fun addExpense(expanse: ExpenseModel) {
        transactionDao.insertTransaction(expanse.toTransactionEntity())
        pagingSource?.invalidate()
    }

    fun getTransactionsPaged(): Flow<PagingData<TransactionDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { transactionDao.getTransactionsPaged() }
        ).flow.map { pagingData ->
            pagingData.map { it.toTransactionLocalModel().toDomain() }
        }.flowOn(Dispatchers.IO)
    }
}