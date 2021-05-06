package com.example.yeelightapp.database.api.interfaces

interface IYeelightAPI {

    suspend fun connect(ip: String): Boolean

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()

    suspend fun setCurrentRGBB(): List<Any>
}