package com.example.nebulatest.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_route")
    data object Transaction : Screen("transactions_route")
}