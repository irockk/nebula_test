package com.example.nebulatest.features.transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebulatest.core.TimeProvider
import com.example.nebulatest.features.balance.domain.BalanceUseCase
import com.example.nebulatest.features.transaction.domain.TransactionUseCase
import com.example.nebulatest.features.transaction.domain.model.ExpenseModel
import com.example.nebulatest.features.transaction.domain.model.TransactionCategory
import com.example.nebulatest.features.transaction.presentation.model.TransactionCategoryPresentationModel
import com.example.nebulatest.features.transaction.presentation.model.toDomain
import com.example.nebulatest.features.transaction.presentation.model.toPresentation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class TransactionState(
    val selectedCategory: TransactionCategoryPresentationModel = TransactionCategory.entries.first()
        .toPresentation()
)

sealed class TransactionEvents {
    data object ShowErrorSnackbar : TransactionEvents()
}

@Factory
class TransactionViewModel(
    private val transactionUseCase: TransactionUseCase,
    private val balanceUseCase: BalanceUseCase,
    private val timeProvider: TimeProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TransactionEvents>()
    val events = _events.receiveAsFlow()

    fun addExpanse(amountText: String) {
        viewModelScope.launch {
            val amount = amountText.toDoubleOrNull()
            if (amount != null) {
                transactionUseCase.addExpense(
                    ExpenseModel(
                        amount,
                        _uiState.value.selectedCategory.toDomain(),
                        timeProvider.getCurrentTimeMillis()
                    )
                )
                balanceUseCase.updateBalance(-amount)
            } else {
                _events.send(TransactionEvents.ShowErrorSnackbar)
            }
        }
    }

    fun selectCategory(category: TransactionCategoryPresentationModel) {
        _uiState.update { uiState ->
            uiState.copy(selectedCategory = category)
        }
    }
}