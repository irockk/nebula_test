package com.example.nebulatest.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebulatest.features.balance.data.BalanceLocalDataSource
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class TransactionState(
    val selectedCategory: TransactionCategory = TransactionCategory.entries.first()
)

sealed class TransactionEvents {
    data object ShowErrorSnackbar : TransactionEvents()
}

@Factory
class TransactionViewModel(
    private val transactionLocalRepository: TransactionLocalRepository,
    private val balanceLocalDataSource: BalanceLocalDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TransactionEvents>()
    val events = _events.receiveAsFlow()

    fun addExpanse(amountText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val amount = amountText.toDoubleOrNull()
            if (amount != null) {
                transactionLocalRepository.addExpense(
                    ExpanseModel(
                        amount,
                        _uiState.value.selectedCategory
                    )
                )
                balanceLocalDataSource.updateBalance(-amount)
            } else {
                _events.send(TransactionEvents.ShowErrorSnackbar)
            }
        }
    }

    fun selectCategory(category: TransactionCategory) {
        _uiState.update { uiState ->
            uiState.copy(selectedCategory = category)
        }
    }
}