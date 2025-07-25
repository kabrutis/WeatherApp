package com.example.weatherapp

import com.example.weatherapp.domain.models.ErrorType
import com.example.weatherapp.domain.models.WeatherInfo
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.core.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var repository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel
    private val dispatcher = StandardTestDispatcher()

    private val mockedWeatherInfo =
        WeatherInfo(
            temperature = 280.0,
            feelsLike = 281.0,
            dateTime = "2025-07-25 09:00:00",
            iconCode = "01d",
            weatherMain = "Clear",
            weatherDescription = "Clear"
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeatherByCity should emit Success result`() = runTest {
        val city = "Dallas"
        val data = listOf(mockedWeatherInfo)
        coEvery { repository.getWeatherByCity(city) } returns flowOf(Result.Success(data))

        viewModel.getWeatherByCity(city)
        advanceUntilIdle()

        val result = viewModel.weatherState.value
        assertTrue(result is Result.Success)
        assertEquals(data, (result as Result.Success).data)
    }

    @Test
    fun `getWeatherByCity should emit Error result on timeout`() = runTest {
        val city = "Dallas"
        val exception = Exception("timeout")
        coEvery { repository.getWeatherByCity(city) } returns flow {
            emit(Result.Error(ErrorType.Timeout, exception))
        }

        viewModel.getWeatherByCity(city)
        advanceUntilIdle()

        val result = viewModel.weatherState.value
        assertTrue(result is Result.Error)
        assertEquals("timeout", (result as Result.Error).exception.message)
    }

    @Test
    fun `getWeatherByCity should emit Error result on 404`() = runTest {
        val city = "asdasdasd"
        val exception = Exception("404 Not Found")
        coEvery { repository.getWeatherByCity(city) } returns flow {
            emit(Result.Error(ErrorType.Network, exception))
        }

        viewModel.getWeatherByCity(city)
        advanceUntilIdle()

        val result = viewModel.weatherState.value
        assertTrue(result is Result.Error)
        assertEquals("404 Not Found", (result as Result.Error).exception.message)
    }

    @Test
    fun `repository should be called once`() = runTest {
        val city = "Dallas"
        coEvery { repository.getWeatherByCity(city) } returns flowOf(Result.Idle)

        viewModel.getWeatherByCity(city)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.getWeatherByCity(city) }
    }

    @Test
    fun `selectWeather updates selectedWeather flow`() = runTest {
        val viewModel = WeatherViewModel(repository)

        viewModel.selectWeather(mockedWeatherInfo)

        assertEquals(mockedWeatherInfo, viewModel.selectedWeather.first())
    }

    @Test
    fun `initial weather state is idle`() = runTest {
        val viewModel = WeatherViewModel(repository)
        assertTrue(viewModel.weatherState.first() is Result.Idle)
    }


}