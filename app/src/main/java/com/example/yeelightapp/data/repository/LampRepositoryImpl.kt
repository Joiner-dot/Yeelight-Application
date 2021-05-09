package com.example.yeelightapp.data.repository

import androidx.lifecycle.LiveData
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.lamps.LampSrc
import com.example.yeelightapp.data.repository.interfaces.LampRepository

class LampRepositoryImpl(private val lampDAO: DataBase, private val yeelightAPI: YeelightAPIImpl) :
    LampRepository {

    val readAllData: LiveData<List<LampSrc>> = lampDAO.selectAllLamps()

    override suspend fun addLamp(lamp: LampSrc) {
        lampDAO.insertNewLamp(lamp)
    }

    override suspend fun deleteLamp(lamp: LampSrc) {
        lampDAO.deleteLamp(lamp)
    }

    override suspend fun connect(ip: String): Boolean {
        return yeelightAPI.connect(ip)
    }

    override suspend fun setCurrentRGBB(): List<Any> {
        return yeelightAPI.setCurrentRGBB()
    }

    override suspend fun changeRGB(red: Int, green: Int, blue: Int) {
        yeelightAPI.changeRGB(red, green, blue)
    }

    override suspend fun changeBrightness(brightness: Int) {
        yeelightAPI.changeBrightness(brightness)
    }

    override suspend fun turnOn() {
        yeelightAPI.turnOn()
    }

    override suspend fun turnOff() {
        yeelightAPI.turnOff()
    }
}