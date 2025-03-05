package com.example.nebulatest

import app.cash.turbine.test
import com.example.nebulatest.core.TimeProvider
import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.TransactionCategory
import com.example.nebulatest.ui.screens.transaction.TransactionEvents
import com.example.nebulatest.ui.screens.transaction.TransactionViewModel
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
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionViewModelUnitTest {
    private val currentTime = 12L

    private lateinit var viewModel: TransactionViewModel

    private val transactionLocalRepository: TransactionLocalRepository = mock()
    private val balanceLocalDataSource: BalanceLocalDataSource = mock()
    private val timeProvider: TimeProvider = mock()

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() = runTest {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        whenever(timeProvider.getCurrentTimeMillis()).thenReturn(currentTime)

        viewModel = TransactionViewModel(
            transactionLocalRepository,
            balanceLocalDataSource,
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
        verify(transactionLocalRepository).addExpense(
            ExpanseModel(amount, viewModel.uiState.value.selectedCategory, currentTime)
        )
        verify(balanceLocalDataSource).updateBalance(-amount)

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
        viewModel.selectCategory(TransactionCategory.TAXI)
        assertEquals(viewModel.uiState.value.selectedCategory, TransactionCategory.TAXI)

        verifyNoMoreInteractions()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(transactionLocalRepository, balanceLocalDataSource)
    }

    @AfterEach
    fun tearDown() = runTest {
        Dispatchers.resetMain()
    }
}