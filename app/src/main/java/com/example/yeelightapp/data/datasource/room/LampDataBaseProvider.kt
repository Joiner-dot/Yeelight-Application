package com.example.yeelightapp.data.datasource.room

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext

class LampDataBaseProvider(context: Context) {

    val instance = Room.databaseBuilder(
        context.applicationContext,
        LampDataBase::class.java,
        "lamps"
    ).build()
}
