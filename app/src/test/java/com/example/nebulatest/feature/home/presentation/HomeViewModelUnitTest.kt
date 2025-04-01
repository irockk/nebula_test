package com.example.nebulatest.feature.home.presentation

import app.cash.turbine.test
import com.example.nebulatest.core.TimeProvider
import com.example.nebulatest.features.balance.domain.BalanceUseCase
import com.example.nebulatest.features.exchange.rate.domain.GetExchangeRateUseCase
import com.example.nebulatest.features.home.presentation.HomeEvents
import com.example.nebulatest.features.home.presentation.HomeViewModel
import com.example.nebulatest.features.transaction.domain.model.IncomeModel
import com.example.nebulatest.features.transaction.domain.TransactionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelUnitTest {
    private val currentTime = 12L

    private val getExchangeRateUseCase: GetExchangeRateUseCase = mock()
    private val transactionUseCase: TransactionUseCase = mock()
    private val balanceUseCase: BalanceUseCase = mock()
    private val timeProvider: TimeProvider = mock()

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() = runTest(testDispatcher) {

        Dispatchers.setMain(testDispatcher)

        whenever(getExchangeRateUseCase.invoke()).thenReturn(Result.success(8432423423.633265))
        whenever(balanceUseCase.observeBalance()).thenReturn(flowOf(1.4636546))
        whenever(transactionUseCase.getTransactionsPaged()).thenReturn(emptyFlow())
        whenever(timeProvider.getCurrentTimeMillis()).thenReturn(currentTime)

        viewModel = HomeViewModel(
            getExchangeRateUseCase,
            transactionUseCase,
            balanceUseCase,
            timeProvider
        )

        advanceUntilIdle()

        verify(balanceUseCase).observeBalance()
        verify(getExchangeRateUseCase).invoke()
        verify(transactionUseCase).getTransactionsPaged()

        verifyNoMoreInteractions()
    }

    @Test
    fun `addIncome and income is null test`() = runTest {
        val textIncome = "0,23,66"

        viewModel.events.test {
            viewModel.addIncome(textIncome)
            assertEquals(HomeEvents.ShowErrorSnackbar, awaitItem())
        }

        verifyNoMoreInteractions()
    }

    @Test
    fun `addIncome and income is not null test`() = runTest {
        val textIncome = "0.23"
        val income = 0.23

        viewModel.addIncome(textIncome)

        advanceUntilIdle()

        verify(transactionUseCase).addIncome(IncomeModel(income, currentTime))
        verify(balanceUseCase).updateBalance(income)

        verifyNoMoreInteractions()
    }


    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(
            transactionUseCase, balanceUseCase, getExchangeRateUseCase
        )
    }

    @AfterEach
    fun tearDown() = runTest {
        Dispatchers.resetMain()
    }
}

