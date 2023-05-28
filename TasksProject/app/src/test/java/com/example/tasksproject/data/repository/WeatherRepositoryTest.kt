package com.example.tasksproject.data.repository

import com.example.tasksproject.data.mapper.WeatherResponseMapper
import com.example.tasksproject.data.network.OpenWeatherApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    private lateinit var weatherRepository: WeatherRepository

    @MockK
    private lateinit var remoteSource: OpenWeatherApiService

    @MockK
    private lateinit var localSource: Any

    @MockK
    private lateinit var weatherResponseMapper: WeatherResponseMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        weatherRepository = WeatherRepository(remoteSource, localSource, weatherResponseMapper)
    }

    @Test
    fun `test getWeatherInfoByCityName with 401 HttpException`() {
        // Arrange
        val cityName = "London"
        val expectedCode = 401
        val expectedErrorMessage = "Unauthorized"
        val responseBody = ResponseBody.create(null, expectedErrorMessage)
        val exception = HttpException(Response.error<ResponseBody>(expectedCode, responseBody))

        coEvery { remoteSource.getWeatherByCityName(city = cityName) } throws exception

        // Act
        runTest {
            val result = runCatching { weatherRepository.getWeatherInfoByCityName(cityName) }
            // Assert

            assertTrue(result.isFailure)
            val error = result.exceptionOrNull()
            assertTrue(error is HttpException)
            assertEquals(expectedCode, (error as HttpException).code())
        }
    }

    @Test
    fun `test getWeatherInfoByCoords with 401 HttpException`() {
        // Arrange
        val latitude = 52.5200f
        val longitude = 13.4050f
        val expectedCode = 401
        val expectedErrorMessage = "Unauthorized"
        val responseBody = ResponseBody.create(null, expectedErrorMessage)
        val exception = HttpException(Response.error<ResponseBody>(expectedCode, responseBody))

        coEvery { remoteSource.getWeatherByCoords(latitude = latitude, longitude = longitude) } throws exception

        // Act
        runTest {
            val result = runCatching { weatherRepository.getWeatherInfoByCoords(latitude, longitude) }

            // Assert
            assertTrue(result.isFailure)
            val error = result.exceptionOrNull()
            assertTrue(error is HttpException)
            assertEquals(expectedCode, (error as HttpException).code())
        }
    }

    @Test
    fun `test getWeatherInfoByCityName with other exception`() = runBlocking {
        // Arrange
        val cityName = "London"
        val expectedErrorMessage = "Illegal state."
        val exception = IllegalStateException(expectedErrorMessage)

        coEvery { remoteSource.getWeatherByCityName(city = cityName) } throws exception

        runTest {
            val result = runCatching { weatherRepository.getWeatherInfoByCityName(cityName) }
            // Assert

            assertTrue(result.isFailure)
            val error = result.exceptionOrNull()
            assertTrue(error is IllegalStateException)
            assertEquals(expectedErrorMessage, error?.message)
        }
    }

    @Test
    fun `test getWeatherInfoByCoords with other exception`() = runBlocking {
        // Arrange
        val latitude = 52.5200f
        val longitude = 13.4050f
        val expectedErrorMessage = "Illegal state."
        val exception = IllegalStateException(expectedErrorMessage)

        coEvery { remoteSource.getWeatherByCoords(latitude = latitude, longitude = longitude) } throws exception

        runTest {
            val result = runCatching { weatherRepository.getWeatherInfoByCoords(latitude = latitude, longitude = longitude) }
            // Assert

            assertTrue(result.isFailure)
            val error = result.exceptionOrNull()
            assertTrue(error is IllegalStateException)
            assertEquals(expectedErrorMessage, error?.message)
        }
    }
}