package com.example.yeelightapp.database.repository

import androidx.lifecycle.LiveData
import com.example.yeelightapp.database.api.YeelightAPI
import com.example.yeelightapp.database.dao.room.LampImpl
import com.example.yeelightapp.database.datasource.Lamp
import com.example.yeelightapp.database.repository.interfaces.ILampRep

class LampRepository(private val lampDAO: LampImpl, private val yeelightAPI: YeelightAPI) :
    ILampRep {

    val readAllData: LiveData<List<Lamp>> = lampDAO.selectAllLamps()

    override suspend fun addLamp(lamp: Lamp) {
        lampDAO.insertNewLamp(lamp)
    }

    override suspend fun deleteLamp(lamp: Lamp) {
        lampDAO.deleteLamp(lamp)
    }

    suspend fun connect(ip: String): Boolean {
        return yeelightAPI.connect(ip)
    }

    suspend fun setCurrentRGBB(): List<Any> {
        return yeelightAPI.setCurrentRGBB()
    }

    suspend fun changeRGB(red:Int, green:Int, blue:Int){
        yeelightAPI.changeRGB(red, green, blue)
    }

    suspend fun changeBrightness(brightness:Int){
        yeelightAPI.changeBrightness(brightness)
    }

    suspend fun turnOn(){
        yeelightAPI.turnOn()
    }

    suspend fun turnOff(){
        yeelightAPI.turnOff()
    }
}