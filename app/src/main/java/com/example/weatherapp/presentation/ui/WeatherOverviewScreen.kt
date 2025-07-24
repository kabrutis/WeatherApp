package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun WeatherOverviewScreen(
    onBack: () -> Unit
) {
    Column {
        Text("overview")
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}