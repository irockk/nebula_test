package com.example.nebulatest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nebulatest.features.home.HomeRoute
import com.example.nebulatest.features.transaction.TransactionRoute

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
        composable(route = Screen.Transition.route) {
            TransactionRoute(navController = navController)
        }
    }
}