package com.example.nebulatest.feature.transaction.presentation

import app.cash.turbine.test
import com.example.nebulatest.core.TimeProvider
import com.example.nebulatest.features.balance.domain.BalanceUseCase
import com.example.nebulatest.features.transaction.domain.TransactionUseCase
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.presentation.TransactionEvents
import com.example.nebulatest.features.transaction.presentation.TransactionViewModel
import com.example.nebulatest.features.transaction.presentation.model.toDomain
import com.example.nebulatest.features.transaction.presentation.model.toPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelUnitTest {
    private val currentTime = 12L

    private lateinit var viewModel: TransactionViewModel

    private val transactionUseCase: TransactionUseCase = mock()
    private val balanceUseCase: BalanceUseCase = mock()
    private val timeProvider: TimeProvider = mock()

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() = runTest {
        Dispatchers.setMain(testDispatcher)

        whenever(timeProvider.getCurrentTimeMillis()).thenReturn(currentTime)

        viewModel = TransactionViewModel(
            transactionUseCase,
            balanceUseCase,
            timeProvider
        )

        verifyNoMoreInteractions()
    }

    @Test
    fun `add expanse and amount is not null test`() = runTest(UnconfinedTestDispatcher()) {
        val amountText = "0.23"
        val amount = 0.23

        viewModel.addExpanse(amountText)

        advanceUntilIdle()

        assertNotNull(amount)
        verify(transactionUseCase).addExpense(
            ExpenseModel(amount, viewModel.uiState.value.selectedCategory.toDomain(), currentTime)
        )
        verify(balanceUseCase).updateBalance(-amount)

        verifyNoMoreInteractions()
    }

    @Test
    fun `add expanse and amount is null test`() = runTest {
        val amountText = "0,23,66"

        viewModel.events.test {
            viewModel.addExpanse(amountText)
            assertEquals(TransactionEvents.ShowErrorSnackbar, awaitItem())
        }

        verifyNoMoreInteractions()
    }

    @Test
    fun `set category test`() {
        val category = TransactionCategory.TAXI.toPresentation()
        viewModel.selectCategory(category)
        assertEquals(viewModel.uiState.value.selectedCategory, category)

        verifyNoMoreInteractions()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(transactionUseCase, balanceUseCase)
    }

    @AfterEach
    fun tearDown() = runTest {
        Dispatchers.resetMain()
    }
}