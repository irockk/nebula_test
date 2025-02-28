package com.example.nebulatest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nebulatest.ui.screens.home.HomeRoute
import com.example.nebulatest.ui.screens.transaction.TransactionRoute

@Composable
fun Navigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeRoute(navController = navController)
        }
        composable(route = Screen.Transaction.route) {
            TransactionRoute(navController = navController)
        }
    }
}