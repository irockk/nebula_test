package com.example.nebulatest.feature.transaction.domain

import androidx.paging.PagingData
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.domain.TransactionUseCase
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.IncomeModel
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.domain.model.TransactionDomainModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class TransactionUseCaseUnitTest {

    private lateinit var useCase: TransactionUseCase
    private val repository: TransactionLocalRepository = mock()

    @BeforeEach
    fun setup() {
        useCase = TransactionUseCase(repository)
    }

    @Test
    fun `add income test`() = runTest {
        val income = IncomeModel(100.0, System.currentTimeMillis())

        useCase.addIncome(income)

        verify(repository).addIncome(income)
    }

    @Test
    fun `add expense test`() = runTest {
        val expense = ExpenseModel(50.0, TransactionCategory.TAXI, System.currentTimeMillis())

        useCase.addExpense(expense)

        verify(repository).addExpense(expense)
    }

    @Test
    fun `get transactions paged return flow test`() = runTest {
        val mockPagingData = PagingData.from(listOf<TransactionDomainModel>())
        whenever(repository.getTransactionsPaged()).thenReturn(flowOf(mockPagingData))

        useCase.getTransactionsPaged()

        verify(repository).getTransactionsPaged()
    }

    @AfterEach
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }
}