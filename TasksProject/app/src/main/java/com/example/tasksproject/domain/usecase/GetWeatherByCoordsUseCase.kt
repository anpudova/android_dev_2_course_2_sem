package com.example.tasksproject.domain.usecase

import com.example.tasksproject.domain.repository.IWeatherRepository
import com.example.tasksproject.presentation.model.WeatherDataModel

class GetWeatherByCoordsUseCase(
    private val weatherRepository: IWeatherRepository
) {

    suspend operator fun invoke(lat: Float, lon: Float): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCoords(lat, lon).mapWeatherEntity()
    }
}