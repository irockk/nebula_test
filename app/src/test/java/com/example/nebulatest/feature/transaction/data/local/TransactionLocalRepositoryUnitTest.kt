package com.example.nebulatest.feature.transaction.data.local

import androidx.paging.PagingSource
import com.example.nebulatest.features.transaction.data.local.TransactionDao
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.data.local.model.TransactionEntity
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.IncomeModel
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.toTransactionEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class TransactionLocalRepositoryUnitTest {

    private val transactionDao: TransactionDao = mock()
    private var pagingSource: PagingSource<Int, TransactionEntity> = mock()
    private lateinit var repository: TransactionLocalRepository

    @BeforeEach
    fun setup() {
        whenever(transactionDao.getTransactionsPaged()).thenReturn(pagingSource)

        repository = TransactionLocalRepository(transactionDao)
    }

    @Test
    fun `add income test`() = runTest {
        val income = IncomeModel(100.0, System.currentTimeMillis())
        repository.addIncome(income)

        verify(transactionDao).insertTransaction(income.toTransactionEntity())
    }


    @Test
    fun `add expense test`() = runTest {
        val expense = ExpenseModel(
            50.0,
            TransactionCategory.TAXI,
            System.currentTimeMillis()
        )

        repository.addExpense(expense)

        verify(transactionDao).insertTransaction(expense.toTransactionEntity())
    }

    @Test
    fun `getTransactionsPaged return paged transactions test`() = runTest {
        repository.getTransactionsPaged()
        verify(transactionDao).getTransactionsPaged()
    }

    @AfterEach
    fun tearDown() {
        verifyNoMoreInteractions(transactionDao, pagingSource)
    }
}