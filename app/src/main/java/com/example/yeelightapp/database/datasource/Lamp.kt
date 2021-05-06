package com.example.yeelightapp.database.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lamps")
data class Lamp(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ip: String
)