package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.utils.weatherIconUrl


@Composable
fun CityLookupScreen(
    viewModel: WeatherViewModel,
    onProceedToDetails: () -> Unit
) {
    val state by viewModel.weatherState.collectAsState()

    var city by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val errorMessage = (state as? Result.Error)?.errorType?.errMessage

    LaunchedEffect(state) {
        if (state is Result.Success<*>) {
            onProceedToDetails()
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { errMessage ->
            snackbarHostState.showSnackbar(errMessage)
            viewModel.resetState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE0F7FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = weatherIconUrl("02d")),
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
                Image(
                    painter = rememberAsyncImagePainter(model = weatherIconUrl("10n")),
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Weather App", fontSize = TextUnit(32.0f, TextUnitType.Sp))
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Enter city") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        keyboardController?.hide()
                        viewModel.getWeatherByCity(city)
                    }) {
                        Text("Lookup")
                    }

                    if (state is Result.Loading) {
                        CircularProgressIndicator(modifier = Modifier.semantics { contentDescription = "Loading" })
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = weatherIconUrl("10d")),
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
                Image(
                    painter = rememberAsyncImagePainter(model = weatherIconUrl("02n")),
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
            }
        }
    }
}