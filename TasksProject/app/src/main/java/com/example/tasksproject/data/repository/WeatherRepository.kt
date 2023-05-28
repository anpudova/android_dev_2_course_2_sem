package com.example.tasksproject.data.repository
import com.example.tasksproject.data.exception.NoNetworkConnectionException
import com.example.tasksproject.data.mapper.WeatherResponseMapper
import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.data.network.OpenWeatherApiService
import com.example.tasksproject.data.network.OpenWeatherService
import com.example.tasksproject.domain.entity.WeatherEntity
import com.example.tasksproject.domain.repository.IWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepository(
    private val remoteSource: OpenWeatherApiService,
    private val localSource: Any,
    private val weatherResponseMapper: WeatherResponseMapper
): IWeatherRepository  {

    override suspend fun getWeatherInfoByCityName(city: String): WeatherEntity {
        return withContext(Dispatchers.IO) {
            try {
                (weatherResponseMapper::map)(remoteSource.getWeatherByCityName(city = city))
            } catch (ex: Exception) {
                when (ex) {
                    is NoNetworkConnectionException -> {
                        (weatherResponseMapper::map)(remoteSource.getWeatherByCityName(city = city))
                    }
                    is HttpException -> {
                        throw HttpException(Response.error<ResponseBody>(ex.code(), ResponseBody.create(null, ex.message())))
                    }
                    else -> {
                        throw IllegalStateException("Illegal state.")
                    }
                }
            }
        }
    }

    override suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherEntity {
        return withContext(Dispatchers.IO) {
            try {
                (weatherResponseMapper::map)(remoteSource.getWeatherByCoords(latitude = latitude, longitude = longitude))
            } catch (ex: Exception) {
                when (ex) {
                    is NoNetworkConnectionException -> {
                        (weatherResponseMapper::map)(remoteSource.getWeatherByCoords(latitude = latitude, longitude = longitude))
                    }
                    is HttpException -> {
                        throw HttpException(Response.error<ResponseBody>(ex.code(), ResponseBody.create(null, ex.message())))
                    }
                    else -> {
                        throw IllegalStateException("Illegal state.")
                    }
                }
            }
        }
    }
}
