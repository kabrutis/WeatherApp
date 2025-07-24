package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.core.utils.TemperatureUtil
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.utils.weatherIconUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherOverviewScreen(
    viewModel: WeatherViewModel,
    onBack: () -> Unit
) {

    val selectedItem by viewModel.selectedWeather.collectAsState()
    val currentCity by viewModel.currentCity.collectAsState()

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
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = weatherIconUrl(selectedItem.iconCode)),
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )

                Text(text = TemperatureUtil.kelvinToFahrenheitString(selectedItem.temperature), fontSize = TextUnit(72.0f, TextUnitType.Sp))

            }

            Spacer(Modifier.size(8.dp))
            Text(text = "Feels Like: ${TemperatureUtil.kelvinToFahrenheitString(selectedItem.feelsLike)}", fontSize = TextUnit(24.0f, TextUnitType.Sp))
            Spacer(Modifier.size(8.dp))
            Text(text = selectedItem.weatherMain, fontSize = TextUnit(32.0f, TextUnitType.Sp))
            Spacer(Modifier.size(8.dp))
            Text(text = selectedItem.weatherDescription, fontSize = TextUnit(24.0f, TextUnitType.Sp))

        }
    }

}