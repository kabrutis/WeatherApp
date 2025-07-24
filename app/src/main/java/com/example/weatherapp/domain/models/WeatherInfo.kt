package com.example.weatherapp.domain.models

data class WeatherInfo(
    val temperature: Double,
    val feelsLike: Double,
    val weatherMain: String,
    val weatherDescription: String,
    val iconCode: String,
    val dateTime: String
) {
    companion object {
        fun empty() = WeatherInfo(
            dateTime = "",
            weatherMain = "No weather selected",
            weatherDescription = "No weather selected",
            temperature = 270.0,
            feelsLike = 270.0,
            iconCode = "01d"
        )
    }
}