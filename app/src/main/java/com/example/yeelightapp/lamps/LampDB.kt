package com.example.yeelightapp.lamps

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lamps")
data class LampDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ip: String
)