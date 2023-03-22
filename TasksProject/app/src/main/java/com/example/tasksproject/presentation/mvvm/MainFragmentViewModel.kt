package com.example.tasksproject.presentation.mvvm

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tasksproject.di.ViewModelArgsKeys
import com.example.tasksproject.domain.usecase.GetWeatherByCityNameUseCase
import com.example.tasksproject.presentation.model.WeatherDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val getWeatherUseCase: GetWeatherByCityNameUseCase
) : ViewModel() {

    private val _progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBarState: LiveData<Boolean> = _progressBarState

    private val _weatherDataState: MutableLiveData<WeatherDataModel?> = MutableLiveData(null)
    val weatherDataState: LiveData<WeatherDataModel?> = _weatherDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    fun requestCityByName(cityName: String) {
        viewModelScope.launch {
            _progressBarState.value = true
            delay(2000L)
            runCatching {
                getWeatherUseCase(cityName)
            }.onSuccess { weatherDataModel ->
                _progressBarState.value = false
                _weatherDataState.postValue(weatherDataModel)
            }.onFailure { ex ->
                _progressBarState.value = false
                _errorState.value = ex
            }
        }
    }

    companion object {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val getWeatherUseCase =
                    extras[ViewModelArgsKeys.getWeatherByCityNameUseCaseKey] ?: throw IllegalArgumentException()
                return (MainFragmentViewModel(getWeatherUseCase) as? T) ?: throw java.lang.IllegalStateException()
            }
        }
    }
}