package com.example.yeelightapp.data.datasource.room

import android.content.Context
import androidx.room.Room
import com.example.yeelightapp.data.dao.LampStorage

class LampDataBaseProvider(context: Context) {

    val appDataBase: AppDataBase = Room.databaseBuilder(
        context.applicationContext,
        AppDataBase::class.java,
        "lamps"
    ).build()
}
