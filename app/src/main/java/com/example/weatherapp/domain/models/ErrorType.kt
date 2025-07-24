package com.example.weatherapp.domain.models

sealed class ErrorType(val errMessage: String) {
    object Network : ErrorType("Unable to find weather. Please check city.")
    object Timeout : ErrorType("Using Render web service to request, service was sleep try again in a few seconds.")
    object Unknown : ErrorType("Unknown error occured.")

    companion object {
        fun fromExceptionMessage(msg: String?): ErrorType {
            val lowerMsg = msg?.lowercase() ?: return Unknown
            return when {
                "404" in lowerMsg -> Network
                "timeout" in lowerMsg -> Timeout
                else -> Unknown
            }
        }
    }
}