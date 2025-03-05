package com.example.nebulatest

import app.cash.turbine.test
import com.example.nebulatest.core.TimeProvider
import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.exchange.rate.domain.GetExchangeRateUseCase
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.ui.screens.home.HomeEvents
import com.example.nebulatest.ui.screens.home.HomeViewModel
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
    private val transactionLocalRepository: TransactionLocalRepository = mock()
    private val balanceLocalDataSource: BalanceLocalDataSource = mock()
    private val timeProvider: TimeProvider = mock()

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() = runTest(testDispatcher) {

        Dispatchers.setMain(testDispatcher)

        whenever(getExchangeRateUseCase.invoke()).thenReturn(Result.success(8432423423.633265))
        whenever(balanceLocalDataSource.getBalance()).thenReturn(flowOf(1.4636546))
        whenever(transactionLocalRepository.getTransactionsPaged()).thenReturn(emptyFlow())
        whenever(timeProvider.getCurrentTimeMillis()).thenReturn(currentTime)

        viewModel = HomeViewModel(
            getExchangeRateUseCase,
            transactionLocalRepository,
            balanceLocalDataSource,
            timeProvider
        )

        advanceUntilIdle()

        verify(balanceLocalDataSource).getBalance()
        verify(getExchangeRateUseCase).invoke()
        verify(transactionLocalRepository).getTransactionsPaged()

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

        verify(transactionLocalRepository).addIncome(IncomeModel(income, currentTime))
        verify(balanceLocalDataSource).updateBalance(income)

        verifyNoMoreInteractions()
    }


    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(
            transactionLocalRepository, balanceLocalDataSource, getExchangeRateUseCase
        )
    }

    @AfterEach
    fun tearDown() = runTest {
        Dispatchers.resetMain()
    }
}

