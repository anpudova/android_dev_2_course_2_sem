package com.example.tasksproject.domain.usecase

import com.example.tasksproject.domain.entity.mapWeatherEntity
import com.example.tasksproject.domain.repository.IWeatherRepository
import com.example.tasksproject.presentation.model.WeatherDataModel

class GetWeatherByCityNameUseCase(
    private val weatherRepository: IWeatherRepository
) {

    suspend operator fun invoke(city: String): WeatherDataModel {
        return weatherRepository.getWeatherInfoByCityName(city).mapWeatherEntity()
    }
}