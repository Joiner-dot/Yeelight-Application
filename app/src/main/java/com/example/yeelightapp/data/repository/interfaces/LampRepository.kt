package com.example.yeelightapp.data.repository.interfaces

import com.example.yeelightapp.lamps.LampSrc

interface LampRepository {

    suspend fun addLamp(lamp: LampSrc)

    suspend fun deleteLamp(lamp: LampSrc)

    suspend fun connect(ip: String): Boolean

    suspend fun deleteByNameAndIp(name:String, ip:String)

    suspend fun setCurrentRGBB(ip: String): List<Any>

    suspend fun changeRGB(red: Int, green: Int, blue: Int)

    suspend fun changeBrightness(brightness: Int)

    suspend fun turnOn()

    suspend fun turnOff()
}