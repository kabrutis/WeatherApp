package com.example.weatherapp.domain.repository

import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.domain.models.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeatherByCity(city: String): Flow<Result<List<WeatherInfo>>>

}