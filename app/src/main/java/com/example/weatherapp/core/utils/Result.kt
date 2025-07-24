package com.example.weatherapp.core.utils

import com.example.weatherapp.domain.models.ErrorType

sealed class Result<out T> {
    object Idle : Result<Nothing>()
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorType: ErrorType, val exception: Throwable) : Result<Nothing>()
}