package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<List<WeatherInfo>>>(Result.Idle)
    val weatherState: StateFlow<Result<List<WeatherInfo>>> = _weatherState.asStateFlow()

    private val _selectedWeather = MutableStateFlow(WeatherInfo.empty())
    val selectedWeather: StateFlow<WeatherInfo> = _selectedWeather.asStateFlow()

    private val _currentCity = MutableStateFlow("")
    val currentCity: StateFlow<String> = _currentCity.asStateFlow()

    fun selectWeather(item: WeatherInfo) {
        _selectedWeather.value = item
    }

    fun getWeatherByCity(city: String) {
        _currentCity.value = city
        viewModelScope.launch {
            repository.getWeatherByCity(city)
                .collect { result ->
                    _weatherState.value = result
                }
        }
    }

    fun resetState() {
        _weatherState.value = Result.Idle
    }

}