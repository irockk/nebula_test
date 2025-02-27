package com.example.nebulatest.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.nebulatest.ui.screens.transaction.goToTransactionScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(navController: NavController) {

    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        goToTransaction = { goToTransactionScreen(navController) }
    )

    HandleHomeEvents(
        events = viewModel.events,
        showSnackbar = {/*TODO */ }
    )
}

@Composable
private fun HandleHomeEvents(
    events: Flow<HomeEvents>,
    showSnackbar: () -> Unit
) {
    LaunchedEffect(Unit) {
        events.collectLatest {
            when (it) {
                is HomeEvents.ShowSnackbar -> showSnackbar()
            }
        }
    }
}


