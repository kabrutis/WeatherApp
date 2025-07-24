package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun WeatherDetailsScreen(
    onBack: () -> Unit
) {
    Column {
        Text("details")
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}