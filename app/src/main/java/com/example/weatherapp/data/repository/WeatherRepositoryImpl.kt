package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherServiceApi
import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.data.mapper.WeatherMapper
import com.example.weatherapp.domain.models.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherServiceApi
) : WeatherRepository {

    override fun getWeatherByCity(city: String): Flow<Result<List<WeatherInfo>>> = flow {
        emit(Result.Loading)
        try {
            val response = api.getWeatherForecastByCity(city)
            val weatherInfo = WeatherMapper.toWeatherInfo(response)
            emit(Result.Success(weatherInfo))
        } catch (e: Exception) {
            val errType = ErrorType.fromExceptionMessage(e.message)
            emit(Result.Error(errType, e))
        }
    }.flowOn(Dispatchers.IO)
}