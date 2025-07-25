package com.example.weatherapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.CityLookupScreen
import com.example.weatherapp.core.utils.Result
import com.example.weatherapp.domain.models.WeatherInfo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CityLookupScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun enterCity_clickLookup_triggersSearch() {
        val testCity = "Dallas"
        val mockViewModel = mockk<WeatherViewModel>(relaxed = true)

        every { mockViewModel.weatherState } returns MutableStateFlow<Result<List<WeatherInfo>>>(Result.Loading)


        composeTestRule.setContent {
            CityLookupScreen(viewModel = mockViewModel, onProceedToDetails = {})
        }

        composeTestRule
            .onNodeWithText("Enter city")
            .performTextInput(testCity)

        composeTestRule
            .onNodeWithText("Lookup")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertExists()
    }
}