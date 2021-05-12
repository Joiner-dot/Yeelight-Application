package com.example.yeelightapp.data.dao

import androidx.lifecycle.LiveData
import com.example.yeelightapp.lamps.LampFromDB

interface DataBase {

    suspend fun insertNewLamp(lamp: LampFromDB)

    fun selectAllLamps(): LiveData<List<LampFromDB>>

    suspend fun deleteByNameAndIp(id:Int)
}