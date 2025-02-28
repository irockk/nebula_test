package com.example.nebulatest.ui.screens.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.nebulatest.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionRoute(navController: NavController) {
    val viewModel = koinViewModel<TransactionViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    TransactionScreen(
        uiState = uiState,
        setSelectedCategory = viewModel::selectCategory,
        saveExpanse = viewModel::addExpanse,
        goBack = { navController.popBackStack() }
    )
}

fun goToTransactionScreen(navController: NavController) {
    navController.navigate(Screen.Transaction.route)
}