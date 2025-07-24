package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.core.utils.TemperatureUtil
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.utils.weatherIconUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(
    viewModel: WeatherViewModel,
    onBack: () -> Unit,
    onWeatherItemClicked: () -> Unit
) {
    val state by viewModel.weatherState.collectAsState()
    val currentCity by viewModel.currentCity.collectAsState()
    val data = (state as? Result.Success)?.data
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentCity) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (data != null) {
            LazyColumn( modifier = Modifier.padding(paddingValues)) {
                itemsIndexed(data) { index, weather ->
                    WeatherDetailItem(
                        dateTime = weather.dateTime,
                        description = weather.weatherMain,
                        temp = TemperatureUtil.kelvinToFahrenheitString(weather.temperature),
                        iconCode = weather.iconCode,
                        onWeatherItemClicked = {
                            viewModel.selectWeather(weather)
                            onWeatherItemClicked()
                        }
                    )
                    if (index < data.lastIndex) {
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    }
                }
            }
        } else {
            Text("No data")
        }
    }
}

@Composable
fun WeatherDetailItem(
    dateTime: String,
    description:String,
    temp: String,
    iconCode: String,
    onWeatherItemClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onWeatherItemClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = weatherIconUrl(iconCode)),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = dateTime)
            Text(text = description)
        }

        Text(text = temp)


    }
}