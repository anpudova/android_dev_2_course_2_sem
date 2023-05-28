package com.example.tasksproject.data.mapper

import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.domain.entity.WeatherEntity

class WeatherResponseMapper {

    fun map(item: WeatherResponse?): WeatherEntity {
        return item?.let { response ->
            with(response) {
                WeatherEntity(
                    city = item.city.toString(),
                    icon = item.weatherList?.get(0)?.icon.toString(),
                    temperature = item.main?.temp ?: 0.0f,
                    pressure = item.main?.pressure ?: 0.0f,
                    humidity = item.main?.humidity ?: 0.0f,
                    speed = item.wind?.speed ?: 0.0f
                )
            }
        } ?: WeatherEntity(
            city = "",
            icon = "",
            temperature = 0.0f,
            pressure = 0.0f,
            humidity = 0.0f,
            speed = 0.0f
        )
    }
}