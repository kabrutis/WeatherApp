package com.example.weatherapp.presentation.navigation

sealed class Routes(val route: String) {
    object Lookup : Routes("lookup")
    object Overview : Routes("overview")
    object Details : Routes("details")
}