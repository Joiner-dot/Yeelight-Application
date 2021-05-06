package com.example.yeelightapp.database.repository.interfaces

import com.example.yeelightapp.database.datasource.Lamp

interface ILampRep {

    suspend fun addLamp(lamp: Lamp)

    suspend fun deleteLamp(lamp: Lamp)
}