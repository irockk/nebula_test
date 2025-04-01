package com.example.nebulatest.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nebulatest.features.home.presentation.HomeRoute
import com.example.nebulatest.features.transaction.presentation.TransactionRoute
import com.example.nebulatest.ui.components.SnackbarHandler

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    SnackbarHandler { snackbarHostState, showSnackbar ->
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(route = Screen.Home.route) {
                    HomeRoute(
                        navController = navController,
                        showSnackbar = showSnackbar
                    )
                }
                composable(route = Screen.Transaction.route) {
                    TransactionRoute(
                        navController = navController,
                        showSnackbar = showSnackbar
                    )
                }
            }
        }
    }
}