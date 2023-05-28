package com.example.tasksproject.domain.usecase

import com.example.tasksproject.data.repository.WeatherRepository
import com.example.tasksproject.domain.entity.WeatherEntity
import com.example.tasksproject.domain.repository.IWeatherRepository
import com.example.tasksproject.presentation.model.WeatherDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherByCityNameUseCaseTest {

    lateinit var useCase: GetWeatherByCityNameUseCase

    @MockK
    lateinit var mockRepository: WeatherRepository

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        useCase = GetWeatherByCityNameUseCase(mockRepository)
    }

    @Test
    fun `Check if weather entity is correctly mapped to weather model`() {
        // Arrange
        val cityName = "London"
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
        coEvery {
            mockRepository.getWeatherInfoByCityName(cityName)
        } returns weatherEntity

        // Act
        runTest {
            val result = useCase(cityName)

            // Assert
            assertEquals(expectedWeatherDataModel, result)
        }
    }

    @Test
    fun `invoke should throw IllegalStateException if exception occurs in repository`() {
        // Arrange
        val city = "London"
        coEvery {
            mockRepository.getWeatherInfoByCityName(city)
        } throws IllegalStateException("Exception occurred")

        // Act & Assert
        runTest {
            assertFailsWith<IllegalStateException> {
                runBlocking { useCase(city) }
            }
        }

    }
}
