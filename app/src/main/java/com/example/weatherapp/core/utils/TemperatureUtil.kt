package com.example.weatherapp.core.utils

import kotlin.math.roundToInt

object TemperatureUtil {

    fun kelvinToFahrenheit(kelvin: Double): Int {
        return ((kelvin - 273.15) * 9/5 + 32).roundToInt()
    }

    fun kelvinToFahrenheitString(kelvin: Double): String {
        val fahrenheit = kelvinToFahrenheit(kelvin)
        return "${fahrenheit}°F"
    }
}