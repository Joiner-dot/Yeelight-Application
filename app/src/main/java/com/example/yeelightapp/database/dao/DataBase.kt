package com.example.yeelightapp.database.dao

import androidx.lifecycle.LiveData
import com.example.yeelightapp.database.datasource.Lamp

interface DataBase {

    suspend fun insertNewLamp(lamp: Lamp)

    suspend fun deleteLamp(lamp: Lamp)

     fun selectAllLamps(): LiveData<List<Lamp>>
}