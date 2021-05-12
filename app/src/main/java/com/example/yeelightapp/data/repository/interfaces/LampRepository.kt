package com.example.yeelightapp.data.repository.interfaces

import com.example.yeelightapp.lamps.LampFromDB
import com.example.yeelightapp.lamps.PropertyForUI


interface LampRepository {

    suspend fun addLamp(lamp: LampFromDB)

    suspend fun deleteLamp(id:Int)

    suspend fun connect(ip: String)

    suspend fun setCurrentRGBB(ip: String): PropertyForUI

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()

    suspend fun turnMode(mode:String)
}