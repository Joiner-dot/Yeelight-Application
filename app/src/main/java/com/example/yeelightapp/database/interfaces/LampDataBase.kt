package com.example.yeelightapp.database.interfaces

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lamp::class], version = 1, exportSchema = false)
abstract class LampDataBase : RoomDatabase() {

    abstract fun iDataBase(): IDataBase

    companion object {
        @Volatile
        private var INSTANCE: LampDataBase? = null

        fun getDatabase(context: Context): LampDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instane = Room.databaseBuilder(
                    context.applicationContext,
                    LampDataBase::class.java,
                    "lamps"
                ).build()
                INSTANCE = instane
                return instane
            }
        }
    }
}