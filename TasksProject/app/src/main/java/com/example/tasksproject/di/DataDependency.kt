package com.example.tasksproject.di

import com.example.tasksproject.data.mapper.WeatherResponseMapper
import com.example.tasksproject.data.network.OpenWeatherApiService
import com.example.tasksproject.data.network.OpenWeatherService
import com.example.tasksproject.data.repository.WeatherRepository
import com.example.tasksproject.domain.repository.IWeatherRepository
import com.example.tasksproject.domain.usecase.GetWeatherByCityNameUseCase
import com.example.tasksproject.domain.usecase.GetWeatherByCoordsUseCase

object DataDependency {

    private val weatherResponseMapper = WeatherResponseMapper()

    private val openWeatherApi: OpenWeatherApiService = OpenWeatherService.getInstance()

    private val weatherRepository: IWeatherRepository = WeatherRepository(
        remoteSource = openWeatherApi,
        localSource = Any(),
        weatherResponseMapper = weatherResponseMapper
    )

    val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase = GetWeatherByCityNameUseCase(weatherRepository)
    val getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase = GetWeatherByCoordsUseCase(weatherRepository)

}