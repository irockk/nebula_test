package com.example.nebulatest.features.transaction.domain

import androidx.paging.PagingData
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.IncomeModel
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class TransactionUseCase(private val transactionLocalRepository: TransactionLocalRepository) {

    suspend fun addIncome(income: IncomeModel) = transactionLocalRepository.addIncome(income)

    suspend fun addExpense(expanse: ExpenseModel) = transactionLocalRepository.addExpense(expanse)

    fun getTransactionsPaged(): Flow<PagingData<TransactionDomainModel>> =
        transactionLocalRepository.getTransactionsPaged()
}