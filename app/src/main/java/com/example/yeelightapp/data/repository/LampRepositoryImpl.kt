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

    override suspend fun deleteByNameAndIp(name: String, ip: String) {
        lampDAO.deleteByNameAndIp(name, ip)
    }

    override suspend fun deleteLamp(lamp: LampSrc) {
        lampDAO.deleteLamp(lamp)
    }

    override suspend fun connect(ip: String): Boolean {
        return yeelightAPI.connect(ip)
    }

    override suspend fun setCurrentRGBB(ip: String): List<Any> {
        return yeelightAPI.setCurrentRGBB(ip)
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

    override suspend fun nightMode() {
        yeelightAPI.nightMode()
    }

    override suspend fun workMode() {
        yeelightAPI.workMode()
    }

    override suspend fun partyMode() {
        yeelightAPI.partyMode()
    }

    override suspend fun romanticMode() {
        yeelightAPI.romanticMode()
    }
}