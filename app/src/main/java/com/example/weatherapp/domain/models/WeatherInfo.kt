package com.example.weatherapp.domain.models

data class WeatherInfo(
    val temperature: Double,
    val feelsLike: Double,
    val weatherMain: String,
    val weatherDescription: String,
    val iconCode: String,
    val dateTime: String
)