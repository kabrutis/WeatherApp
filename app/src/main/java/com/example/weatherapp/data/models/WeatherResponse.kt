package com.example.weatherapp.data.models

data class WeatherResponse(
    val list: List<Forecast>,
    val city: City
)
