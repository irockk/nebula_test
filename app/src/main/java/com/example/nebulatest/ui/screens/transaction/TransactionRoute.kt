package com.example.nebulatest.ui.screens.transaction

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nebulatest.ui.navigation.Screen

@Composable
fun TransactionRoute(navController: NavController) {
    TransactionScreen(
        goBack = { navController.popBackStack() }
    )
}

fun goToTransactionScreen(navController: NavController) {
    navController.navigate(Screen.Transaction.route)
}