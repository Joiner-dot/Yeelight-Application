package com.example.yeelightapp.data.repository

import androidx.lifecycle.LiveData
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.lamps.LampFromDB
import com.example.yeelightapp.lamps.PropertyForUI


class LampRepositoryImpl(private val lampDAO: DataBase, private val yeelightAPI: YeelightAPI) :
    LampRepository {

    val readAllData: LiveData<List<LampFromDB>> = lampDAO.selectAllLamps()

    override suspend fun addLamp(lamp: LampFromDB) {
        lampDAO.insertNewLamp(lamp)
    }

    override suspend fun deleteLamp(id:Int) {
        lampDAO.deleteByNameAndIp(id)
    }

    override suspend fun connect(ip: String) {
        return yeelightAPI.connect(ip)
    }

    override suspend fun setCurrentRGBB(ip: String): PropertyForUI {
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

    override suspend fun turnMode(mode: String) {
        when(mode){
            "Night" -> yeelightAPI.nightMode()
            "Work" -> yeelightAPI.workMode()
            "Party" -> yeelightAPI.partyMode()
            "Romantic" -> yeelightAPI.romanticMode()
        }
    }
}