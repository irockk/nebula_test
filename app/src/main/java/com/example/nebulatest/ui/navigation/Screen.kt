package com.example.nebulatest.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_route")
    data object Transition : Screen("transition_route")
}