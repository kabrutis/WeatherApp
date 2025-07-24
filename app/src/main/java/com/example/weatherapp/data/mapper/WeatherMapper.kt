package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.domain.models.WeatherInfo

object WeatherMapper {

    fun toWeatherInfo(response: WeatherResponse): List<WeatherInfo> {
        return response.list.map { forecast ->
            WeatherInfo(
                temperature = forecast.main.temp,
                feelsLike = forecast.main.feels_like,
                weatherMain = forecast.weather.firstOrNull()?.main ?: "Unknown",
                weatherDescription = forecast.weather.firstOrNull()?.description ?: "Unknown",
                iconCode = forecast.weather.firstOrNull()?.icon.orEmpty(),
                dateTime = forecast.dt_txt
            )
        }
    }
}