package com.example.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.CityLookupScreen
import com.example.weatherapp.presentation.ui.WeatherDetailsScreen
import com.example.weatherapp.presentation.ui.WeatherOverviewScreen

@Composable
fun WeatherNavGraph(navController: NavHostController = rememberNavController()) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()

    NavHost(navController, startDestination = Routes.Lookup.route) {

        composable(Routes.Lookup.route) {
            CityLookupScreen(
                weatherViewModel,
                onProceedToDetails = { navController.navigate(Routes.Details.route) }
            )
        }

        composable(Routes.Overview.route) {
            WeatherOverviewScreen(weatherViewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.Details.route) {
            WeatherDetailsScreen(weatherViewModel, onBack = {
                weatherViewModel.resetState()
                navController.popBackStack()
            }, onWeatherItemClicked = { navController.navigate(Routes.Overview.route) })
        }
    }
}