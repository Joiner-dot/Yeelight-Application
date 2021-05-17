package com.example.yeelightapp.data.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yeelightapp.data.dao.room.LampDAO
import com.example.yeelightapp.lamps.LampDB

@Database(entities = [LampDB::class], version = 1, exportSchema = false)
abstract class LampDataBase : RoomDatabase() {

    abstract fun lampDAO(): LampDAO

    companion object {
        @Volatile
        private var INSTANCE: LampDataBase? = null

        fun getDatabase(context: Context): LampDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            val instance: LampDataBase by lazy {
                Room.databaseBuilder(
                    context.applicationContext,
                    LampDataBase::class.java,
                    "lamps"
                ).build()
            }
            INSTANCE = instance
            return instance
        }
    }
}