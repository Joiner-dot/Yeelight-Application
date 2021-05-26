package com.example.yeelightapp.data.api.interfaces

import com.example.yeelightapp.lamps.PropertyForUI

interface YeelightAPI {

    suspend fun connect(ip: String)

    suspend fun changeRGB(red: Int, green: Int, blue: Int):String

    suspend fun changeBrightness(brightness: Int):String

    suspend fun turnOn():String

    suspend fun turnOff():String

    suspend fun setCurrentRGBB(ip: String): PropertyForUI

    suspend fun nightMode():String

    suspend fun workMode():String

    suspend fun partyMode():String

    suspend fun closeConnection()

    suspend fun romanticMode():String
}