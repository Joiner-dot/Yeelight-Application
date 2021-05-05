package com.example.yeelightapp.database.interfaces

import androidx.lifecycle.LiveData

class LampRepository(private val iDataBase: IDataBase) {

    val readAllData:LiveData<List<Lamp>> = iDataBase.selectAllLamps()

    suspend fun addLamp(lamp: Lamp){
        iDataBase.insertNewLamp(lamp)
    }

    suspend fun deleteLamp(lamp: Lamp){
        iDataBase.deleteLamp(lamp)
    }
}