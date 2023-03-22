package com.example.tasksproject.domain.entity

import com.example.tasksproject.presentation.model.WeatherDataModel

data class WeatherEntity(
    val city: String,
    val icon: String,
    val temperature: Float,
    val pressure: Float,
    val humidity: Float,
    val speed: Float
) {
    fun mapWeatherEntity(): WeatherDataModel {
        return WeatherDataModel(
            city = this.city,
            icon = this.icon,
            temperature = this.temperature,
            pressure = this.pressure,
            humidity = this.humidity,
            speed = this.speed
        )
    }
}

