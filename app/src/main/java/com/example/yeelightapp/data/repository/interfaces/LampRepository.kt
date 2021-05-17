package com.example.yeelightapp.data.repository.interfaces

import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.lamps.LampDB
import com.example.yeelightapp.lamps.PropertyForUI


interface LampRepository {

    suspend fun addLamp(lamp: LampDB)

    suspend fun deleteLamp(lamp: LampDB)

    suspend fun connect(ip: String)

    suspend fun setCurrentRGBB(ip: String): PropertyForUI

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()

    suspend fun turnMode(mode:Modes)
}