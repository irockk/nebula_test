package com.example.nebulatest.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebulatest.features.transaction.data.local.TransactionLocalRepository
import com.example.nebulatest.features.transaction.model.ExpanseModel
import com.example.nebulatest.features.transaction.model.TransactionCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

data class TransactionState(
    val selectedCategory: TransactionCategory = TransactionCategory.entries.first()
)

@Factory
class TransactionViewModel(
    private val transactionLocalRepository: TransactionLocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState = _uiState.asStateFlow()

    fun addExpanse(amount: Double) {
        viewModelScope.launch {
            transactionLocalRepository.addExpense(
                ExpanseModel(
                    amount,
                    _uiState.value.selectedCategory
                )
            )
        }
    }

    fun selectCategory(category: TransactionCategory) {
        _uiState.update { uiState ->
            uiState.copy(selectedCategory = category)
        }
    }
}