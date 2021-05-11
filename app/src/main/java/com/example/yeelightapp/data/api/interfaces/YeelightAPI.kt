package com.example.yeelightapp.data.api.interfaces

interface YeelightAPI {

    suspend fun connect(ip: String): Boolean

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()

    suspend fun setCurrentRGBB(ip: String): List<Any>

    suspend fun nightMode()

    suspend fun workMode()

    suspend fun partyMode()

    suspend fun romanticMode()
}