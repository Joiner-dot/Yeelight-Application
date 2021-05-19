package com.example.yeelightapp.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yeelightapp.data.dao.room.LampDAO
import com.example.yeelightapp.lamps.LampDB

@Database(entities = [LampDB::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun lampDAO(): LampDAO
}