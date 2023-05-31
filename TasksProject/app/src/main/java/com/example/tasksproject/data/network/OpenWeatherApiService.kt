package com.example.tasksproject.data.network

import com.example.tasksproject.data.model.response.WeatherResponse
import retrofit2.http.*

interface OpenWeatherApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCoords(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): WeatherResponse
}