package com.example.tasksproject.db

import android.content.Context
import androidx.room.Room
import com.example.tasksproject.db.entity.CityEntity
import com.example.tasksproject.db.mapper.CityMapper
import com.example.tasksproject.db.model.CityModel
import com.example.tasksproject.db.model.CityUpdateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseHandler {

    const val versiondb: Int = 1
    const val namedb: String = "databaseWeather"
    private var roomDatabase: InceptionDatabase? = null

    fun dbInit(appContext: Context) {
        if (roomDatabase == null) {
            roomDatabase = Room.databaseBuilder(
                appContext,
                InceptionDatabase::class.java,
                namedb
            ).build()
        }
    }

    suspend fun createCity(city: CityModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getCityDao()?.createCity(CityMapper.mapCityEntity(city))
        }
    }

    suspend fun updateCity(city: CityUpdateModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getCityDao()?.updateCityData(city)
        }
    }

    suspend fun getCity(name: String): CityModel? {
        return withContext(Dispatchers.IO) {
            val city: CityEntity? = roomDatabase?.getCityDao()?.getCity(name)
            CityMapper.mapCityModel(city)
        }
    }

    suspend fun getCities(): List<CityModel?> {
        return withContext(Dispatchers.IO) {
            val cities: List<CityEntity?>? = roomDatabase?.getCityDao()?.getCities()
            CityMapper.mapCitiesModel(cities)
        }
    }
}