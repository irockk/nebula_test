package com.example.nebulatest.features.transaction.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.nebulatest.R
import com.example.nebulatest.ui.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionRoute(
    navController: NavController,
    showSnackbar: (String) -> Unit
) {
    val viewModel = koinViewModel<TransactionViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    TransactionScreen(
        uiState = uiState,
        setSelectedCategory = viewModel::selectCategory,
        saveExpanse = viewModel::addExpanse,
        goBack = { navController.popBackStack() }
    )

    HandleTransactionEvents(
        events = viewModel.events,
        showSnackbar = { showSnackbar(context.getString(R.string.default_error_snackbar_text)) }
    )
}

fun goToTransactionScreen(navController: NavController) {
    navController.navigate(Screen.Transaction.route)
}

@Composable
private fun HandleTransactionEvents(
    events: Flow<TransactionEvents>,
    showSnackbar: () -> Unit
) {
    LaunchedEffect(Unit) {
        events.collectLatest {
            when (it) {
                is TransactionEvents.ShowErrorSnackbar -> showSnackbar()
            }
        }
    }
}