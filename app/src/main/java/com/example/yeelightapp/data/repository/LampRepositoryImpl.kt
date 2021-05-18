package com.example.yeelightapp.data.repository

import androidx.lifecycle.LiveData
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.lamps.LampDB
import com.example.yeelightapp.lamps.PropertyForUI


class LampRepositoryImpl(private val dataBase: DataBase, private val yeelightAPI: YeelightAPI) :
    LampRepository {

    override val readAllData: LiveData<List<LampDB>> = dataBase.selectAllLamps()

    override suspend fun addLamp(lamp: LampDB) {
        dataBase.insertNewLamp(lamp)
    }

    override suspend fun deleteLamp(id: Int) {
        dataBase.deleteLamp(id)
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

    override suspend fun turnMode(mode: Modes) {
        when (mode) {
            Modes.Night -> yeelightAPI.nightMode()
            Modes.Work -> yeelightAPI.workMode()
            Modes.Party -> yeelightAPI.partyMode()
            Modes.Romantic -> yeelightAPI.romanticMode()
        }
    }
}