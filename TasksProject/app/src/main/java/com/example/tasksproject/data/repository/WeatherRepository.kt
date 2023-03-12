package com.example.tasksproject.data.repository
import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.data.network.OpenWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String): WeatherResponse {
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance().getWeatherByCityName(city = city)
        }
    }

    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherResponse {
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance().getWeatherByCoords(latitude = latitude, longitude = longitude)
        }
    }
}