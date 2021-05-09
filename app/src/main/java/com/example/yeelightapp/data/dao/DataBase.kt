package com.example.yeelightapp.data.dao

import androidx.lifecycle.LiveData
import com.example.yeelightapp.lamps.LampSrc

interface DataBase {

    suspend fun insertNewLamp(lamp: LampSrc)

    suspend fun deleteLamp(lamp: LampSrc)

     fun selectAllLamps(): LiveData<List<LampSrc>>
}