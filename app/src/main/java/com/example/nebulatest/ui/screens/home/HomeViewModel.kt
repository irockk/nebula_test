package com.example.nebulatest.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.exchange.rate.domain.GetExchangeRateUseCase
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.IncomeModel
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
import com.example.nebulatest.features.transaction.model.presentation.toTransactionPresentationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class HomeState(
    val balance: Double = 0.0,
    val exchangeRate: Double? = null,
    val transactions: Flow<PagingData<TransactionPresentationModel>> = emptyFlow()
)

sealed class HomeEvents {
    data object ShowErrorSnackbar : HomeEvents()
}

@Factory
class HomeViewModel(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
    private val transactionLocalRepository: TransactionLocalRepository,
    private val balanceLocalDataSource: BalanceLocalDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvents>()
    val events = _events.receiveAsFlow()

    init {
        observeBalance()
        setExchangeRate()
        setTransactions()
    }

    private fun observeBalance() {
        viewModelScope.launch {
            balanceLocalDataSource.getBalance().collectLatest { balance ->
                _uiState.update { uiState -> uiState.copy(balance = balance) }
            }
        }
    }

    private fun setTransactions() {
        val transactionsFlow =
            transactionLocalRepository.getTransactionsPaged().cachedIn(viewModelScope)
                .map { it.map { transactions -> transactions.toTransactionPresentationModel() } }
        _uiState.update { it.copy(transactions = transactionsFlow) }
    }

    fun addIncome(incomeText: String) {
        viewModelScope.launch {
            val income = incomeText.toDoubleOrNull()
            if (income != null) {
                transactionLocalRepository.addIncome(IncomeModel(income))
                balanceLocalDataSource.updateBalance(income)
            } else {
                _events.send(HomeEvents.ShowErrorSnackbar)
            }
        }
    }

    private fun setExchangeRate() {
        viewModelScope.launch {
            val result = getExchangeRateUseCase.invoke()
            if (result.isSuccess) {
                _uiState.update { uiState -> uiState.copy(exchangeRate = result.getOrNull()) }
            } else {
                _events.send(HomeEvents.ShowErrorSnackbar)
            }
        }
    }
}