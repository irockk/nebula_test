package com.example.nebulatest.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.nebulatest.features.transaction.goToTransactionScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(navController: NavController) {

    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        goToTransaction = { goToTransactionScreen(navController) }
    )
}
