package com.example.tasksproject.db.mapper

import com.example.tasksproject.db.entity.CityEntity
import com.example.tasksproject.db.model.CityModel

object CityMapper {

    fun mapCityEntity(city: CityModel): CityEntity {
        with(city) {
            return CityEntity(
                id, name, temp, icon
            )
        }
    }

    fun mapCityModel(city: CityEntity?): CityModel? {
        if (city != null) {
            with(city) {
                return CityModel(
                    id, name, temp, icon
                )
            }
        } else {
            return null
        }
    }

    fun mapCitiesModel(cities: List<CityEntity?>?): List<CityModel?> {
        var list = arrayListOf<CityModel?>()
        if (cities != null) {
            for (i in cities.indices) {
                with(cities[i]) {
                    list.add(CityModel(
                        this?.id ?: 0,
                        this?.name ?: "",
                        this?.temp ?: 0.0F,
                        this?.icon ?: ""
                    ))
                }
            }
        }
        return list
    }
}