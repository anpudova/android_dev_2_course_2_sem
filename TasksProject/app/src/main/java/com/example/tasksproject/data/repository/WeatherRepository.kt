package com.example.tasksproject.data.repository
import com.example.tasksproject.data.mapper.WeatherResponseMapper
import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.data.network.OpenWeatherApiService
import com.example.tasksproject.data.network.OpenWeatherService
import com.example.tasksproject.domain.entity.WeatherEntity
import com.example.tasksproject.domain.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val remoteSource: OpenWeatherApiService,
    private val localSource: Any,
    private val weatherResponseMapper: WeatherResponseMapper
): IWeatherRepository  {

    override suspend fun getWeatherInfoByCityName(city: String): WeatherEntity {
        return withContext(Dispatchers.IO) {
            (weatherResponseMapper::map)(remoteSource.getWeatherByCityName(city = city))
        }
    }

    override suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherEntity {
        return withContext(Dispatchers.IO) {
            (weatherResponseMapper::map)(remoteSource.getWeatherByCoords(latitude = latitude, longitude = longitude))
        }
    }
}
