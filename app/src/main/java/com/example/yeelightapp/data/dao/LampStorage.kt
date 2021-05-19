package com.example.yeelightapp.data.dao

import androidx.lifecycle.LiveData
import com.example.yeelightapp.lamps.LampDB

interface LampStorage {

    suspend fun insertNewLamp(lamp: LampDB)

    fun selectAllLamps(): LiveData<List<LampDB>>

    suspend fun deleteLamp(id:Int)
}