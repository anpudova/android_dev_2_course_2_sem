package com.example.tasksproject.di

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tasksproject.domain.usecase.GetWeatherByCityNameUseCase
import com.example.tasksproject.domain.usecase.GetWeatherByCoordsUseCase

object ViewModelArgsKeys {

    val getWeatherByCityNameUseCaseKey = object : CreationExtras.Key<GetWeatherByCityNameUseCase> {}
    val getWeatherByCoordsUseCaseKey = object : CreationExtras.Key<GetWeatherByCoordsUseCase> {}
}