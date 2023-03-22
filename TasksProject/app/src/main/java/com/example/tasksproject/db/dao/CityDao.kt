package com.example.tasksproject.db.dao

import androidx.room.*
import com.example.tasksproject.db.entity.CityEntity
import com.example.tasksproject.db.model.CityModel
import com.example.tasksproject.db.model.CityUpdateModel

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createCity(city: CityEntity)

    @Update(entity = CityEntity::class)
    suspend fun updateCityData(cityDataModel: CityUpdateModel)

    @Query("select * from cities where name = :name")
    suspend fun getCity(name: String): CityEntity?

    @Query("select * from cities")
    suspend fun getCities(): List<CityEntity?>
}