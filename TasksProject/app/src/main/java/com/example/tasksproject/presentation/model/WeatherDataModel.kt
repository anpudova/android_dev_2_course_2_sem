package com.example.tasksproject.presentation.model

data class WeatherDataModel(
    val city: String = "",
    val icon: String = "",
    val temperature: Float = 0.0F,
    val pressure: Float = 0.0F,
    val humidity: Float = 0.0F,
    val speed: Float = 0.0F
) {
    constructor(city: String, icon: String, temperature: Float) : this()
}