package com.example.nebulatest.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebulatest.features.exchange.rate.data.ExchangeRateRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class HomeState(
    val balance: Int = 0,
    val exchangeRate: String? = null
)

sealed class HomeEvents {
    data object ShowSnackbar : HomeEvents()
}

@Factory
class HomeViewModel(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<HomeEvents>()
    val events = _events.receiveAsFlow()

    init {
        setExchangeRate()
    }

    private fun setExchangeRate() {
        viewModelScope.launch {
            val result = exchangeRateRepository.getExchangeRate()
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(exchangeRate = result.getOrNull())
            } else {
                _events.send(HomeEvents.ShowSnackbar)
            }
        }
    }
}