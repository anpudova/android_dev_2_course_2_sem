package com.example.tasksproject.presentation.mvvm

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tasksproject.R
import com.example.tasksproject.di.DataDependency
import com.example.tasksproject.di.ViewModelArgsKeys
import com.example.tasksproject.domain.usecase.GetWeatherByCityNameUseCase
import com.example.tasksproject.domain.usecase.GetWeatherByCoordsUseCase
import com.example.tasksproject.presentation.model.WeatherDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FirstFragmentViewModel (
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getWeatherByCoordsUseCase: GetWeatherByCoordsUseCase,
) : ViewModel() {

    private val _progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBarState: LiveData<Boolean> = _progressBarState

    private val _weatherDataState: MutableLiveData<WeatherDataModel?> =
        MutableLiveData(null)
    val weatherDataState: LiveData<WeatherDataModel?> = _weatherDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    fun requestCityByName(cityName: String) {
        viewModelScope.launch {
            runCatching {
                getWeatherByCityNameUseCase(cityName)
            }.onSuccess {
                _progressBarState.value = false
                _weatherDataState.postValue(it)
            }.onFailure {
                _progressBarState.value = false
                _errorState.value = it
            }
        }
    }

    fun requestCityByCoords(lat: Float, lon: Float) {
        viewModelScope.launch {
            runCatching {
                getWeatherByCoordsUseCase(lat, lon)
            }.onSuccess {
                _progressBarState.value = false
                _weatherDataState.postValue(it)
            }.onFailure {
                _progressBarState.value = false
                _errorState.value = it
            }
        }
    }

    companion object {

    val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val getWeatherByCityNameUseCase =
                extras[ViewModelArgsKeys.getWeatherByCityNameUseCaseKey]
                    ?: throw IllegalArgumentException()
            val getWeatherByCoordsUseCase =
                extras[ViewModelArgsKeys.getWeatherByCoordsUseCaseKey]
                    ?: throw IllegalArgumentException()
            return (FirstFragmentViewModel(getWeatherByCityNameUseCase, getWeatherByCoordsUseCase) as? T)
                ?: throw java.lang.IllegalStateException()
        }
    }
    }
}