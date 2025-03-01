package com.example.nebulatest

import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.exchange.rate.domain.GetExchangeRateUseCase
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.ui.screens.home.HomeEvents
import com.example.nebulatest.ui.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val getExchangeRateUseCase = Mockito.mock(GetExchangeRateUseCase::class.java)
    private val transactionLocalRepository = Mockito.mock(TransactionLocalRepository::class.java)
    private val balanceLocalDataSource: BalanceLocalDataSource =
        Mockito.mock(BalanceLocalDataSource::class.java)

    @Mock
    lateinit var eventsChannel: Channel<HomeEvents>

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        runBlocking {
            whenever(getExchangeRateUseCase.invoke()).thenReturn(Result.success(8432423423.633265))
            whenever(balanceLocalDataSource.getBalance()).thenReturn(flowOf(1.4636546))
            whenever(transactionLocalRepository.getTransactionsPaged()).thenReturn(emptyFlow())
        }

        viewModel = HomeViewModel(
            getExchangeRateUseCase,
            transactionLocalRepository,
            balanceLocalDataSource
        )
    }

    @Test
    fun `addIncome and income is null`() = runTest {
        val textIncome = "0,23,66"
        viewModel.addIncome(textIncome)
        verify(eventsChannel).send(HomeEvents.ShowErrorSnackbar)
    }

    @Test
    fun `addIncome and income is not null`() = runTest {
        val textIncome = "0.23"
        val income = textIncome.toDoubleOrNull()

        viewModel.addIncome(textIncome)

        requireNotNull(income)
        verify(transactionLocalRepository).addIncome(IncomeModel(income))
        verify(balanceLocalDataSource).updateBalance(income)
    }

    @AfterEach
    fun tearDown() = runTest {
        Dispatchers.resetMain()
        Mockito.verifyNoMoreInteractions(
            transactionLocalRepository,
            balanceLocalDataSource,
            getExchangeRateUseCase
        )
    }
}
