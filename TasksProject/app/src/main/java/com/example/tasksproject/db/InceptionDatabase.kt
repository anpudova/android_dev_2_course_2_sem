package com.example.tasksproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasksproject.db.dao.CityDao
import com.example.tasksproject.db.entity.CityEntity

@Database(entities = [CityEntity::class], version = DatabaseHandler.versiondb)
abstract class InceptionDatabase: RoomDatabase() {

    abstract fun getCityDao(): CityDao

}