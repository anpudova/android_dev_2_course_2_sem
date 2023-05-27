package com.example.tasksproject.domain.repository

import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.domain.entity.WeatherEntity

interface IWeatherRepository {

    suspend fun getWeatherInfoByCityName(city: String): WeatherEntity

    suspend fun getWeatherInfoByCoords(latitude: Float, longitude: Float): WeatherEntity

}