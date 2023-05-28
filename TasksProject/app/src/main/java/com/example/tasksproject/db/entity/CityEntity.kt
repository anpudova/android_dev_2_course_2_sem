package com.example.tasksproject.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cities", indices = [Index("name", unique = true)])
data class CityEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val temp: Float,
    val icon: String
)