package com.example.nebulatest.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebulatest.features.exchange.rate.domain.GetExchangeRateUseCase
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.presentation.TransactionPresentationModel
import com.example.nebulatest.features.transaction.model.presentation.toTransactionPresentationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class HomeState(
    val balance: Int = 0,
    val exchangeRate: Double? = null,
    val transactions: List<TransactionPresentationModel> = emptyList()
)

sealed class HomeEvents {
    data object ShowSnackbar : HomeEvents()
}

@Factory
class HomeViewModel(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
    private val transactionLocalRepository: TransactionLocalRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvents>()
    val events = _events.receiveAsFlow()

    init {
        setExchangeRate()
        setTransactions()
    }

    private fun setTransactions() {
        viewModelScope.launch {
            val transactions = transactionLocalRepository.getAllTransactions()
                .map { it.toTransactionPresentationModel() }
            _uiState.update { uiState -> uiState.copy(transactions = transactions) }
        }
    }

    private fun setExchangeRate() {
        viewModelScope.launch {
            val result = getExchangeRateUseCase.invoke()
            if (result.isSuccess) {
                _uiState.update { uiState -> uiState.copy(exchangeRate = result.getOrNull()) }
            } else {
                _events.send(HomeEvents.ShowSnackbar)
            }
        }
    }
}