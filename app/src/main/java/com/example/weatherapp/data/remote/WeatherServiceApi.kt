package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceApi {

    @GET("weather")
    suspend fun getWeatherForecastByCity(
        @Query("city") city: String
    ): WeatherResponse
}