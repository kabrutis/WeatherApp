package com.example.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.ui.CityLookupScreen
import com.example.weatherapp.presentation.ui.WeatherDetailsScreen
import com.example.weatherapp.presentation.ui.WeatherOverviewScreen

@Composable
fun WeatherNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Routes.Lookup.route) {

        composable(Routes.Lookup.route) {
            CityLookupScreen(
                onBack = {
                    navController.navigate(Routes.Overview.route)
                }
            )
        }

        composable(Routes.Overview.route) {
            WeatherDetailsScreen (onBack = { navController.popBackStack() })
        }

        composable(Routes.Details.route) {
            WeatherOverviewScreen(onBack = { navController.popBackStack() })
        }
    }
}