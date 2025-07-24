package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField

import coil.compose.rememberImagePainter

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.navigation.WeatherNavGraph
import com.example.weatherapp.presentation.ui.utils.weatherIconUrl
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    //WeatherScreen()
                    WeatherNavGraph()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.weatherState.collectAsState()

    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.getWeatherByCity(city) }) {
            Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is Result.Idle -> {
                //NOOP user has not entered city yet
            }
            is Result.Loading -> {
                CircularProgressIndicator()
            }
            is Result.Success<*> -> {
                val forcasts = (state as Result.Success).data
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(forcasts) { weather ->

                        Text("Date Time: ${weather.dateTime}")
                        Text("Temp: ${weather.temperature} K")
                        Text("Feel like: ${weather.feelsLike} K")
                        Text("Main: ${weather.weatherMain}")
                        Text("Desc: ${weather.weatherDescription}%")
                        Image(
                            painter = rememberAsyncImagePainter(model = weatherIconUrl(weather.iconCode)),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.size(2.dp).background(Color.Black))
                    }

                }
            }
            is Result.Error -> {
                Text(
                    text = "Error: ${(state as Result.Error).exception.localizedMessage ?: "Unknown error"}",
                    color = Color.Red
                )
            }
        }
    }
}