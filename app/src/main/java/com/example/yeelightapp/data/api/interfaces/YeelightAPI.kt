package com.example.yeelightapp.data.api.interfaces

import com.example.yeelightapp.lamps.PropertyForUI

interface YeelightAPI {

    suspend fun connect(ip: String)

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()

    suspend fun setCurrentRGBB(ip: String): PropertyForUI

    suspend fun nightMode()

    suspend fun workMode()

    suspend fun partyMode()

    suspend fun romanticMode()
}