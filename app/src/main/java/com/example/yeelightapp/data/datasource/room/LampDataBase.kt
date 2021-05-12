package com.example.yeelightapp.data.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yeelightapp.data.dao.room.LampDB
import com.example.yeelightapp.lamps.LampFromDB

@Database(entities = [LampFromDB::class], version = 1, exportSchema = false)
abstract class LampDataBase : RoomDatabase() {

    abstract fun lampDAO(): LampDB

    companion object {
        @Volatile
        private var INSTANCE: LampDataBase? = null

        fun getDatabase(context: Context): LampDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LampDataBase::class.java,
                    "lamps"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}