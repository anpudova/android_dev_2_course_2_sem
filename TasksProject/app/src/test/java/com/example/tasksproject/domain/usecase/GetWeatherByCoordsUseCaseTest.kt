package com.example.tasksproject.domain.usecase

import com.example.tasksproject.data.repository.WeatherRepository
import com.example.tasksproject.domain.entity.WeatherEntity
import com.example.tasksproject.presentation.model.WeatherDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherByCoordsUseCaseTest {

    private lateinit var useCase: GetWeatherByCoordsUseCase

    @MockK
    private lateinit var mockRepository: WeatherRepository

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        useCase = GetWeatherByCoordsUseCase(weatherRepository = mockRepository)
    }

    @Test
    fun `Check if weather entity is correctly mapped to weather model`() {
        // Arrange
        val weatherEntity = mockk<WeatherEntity> {
            every { city } returns "London"
            every { icon } returns "icon"
            every { temperature } returns 25.0f
            every { pressure } returns 1012.0f
            every { humidity } returns 60.0f
            every { speed } returns 10.0f
        }
        val expectedWeatherDataModel = WeatherDataModel(
            city = "London",
            icon = "icon",
            temperature = 25.0f,
            pressure = 1012.0f,
            humidity = 60.0f,
            speed = 10.0f
        )
        val lat = 52.5200f
        val lon = 13.4050f

        coEvery {
            mockRepository.getWeatherInfoByCoords(lat, lon)
        } returns weatherEntity

        // Act
        runTest {
            val result = useCase(lat = lat, lon = lon)
            // Assert
            assertEquals(expectedWeatherDataModel, result)
        }
    }

    @Test
    fun `Check if use case throws IllegalStateException when exception occurs in repository`() {
        // Arrange
        val lat = 52.5200f
        val lon = 13.4050f
        coEvery {
            mockRepository.getWeatherInfoByCoords(lat, lon)
        } throws IllegalStateException("Exception occurred")

        // Act & Assert
        runTest {
            assertFailsWith<IllegalStateException> {
                runBlocking { useCase(lat, lon) }
            }
        }
    }
}