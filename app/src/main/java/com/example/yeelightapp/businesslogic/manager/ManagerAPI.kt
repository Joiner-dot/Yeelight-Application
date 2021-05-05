package com.example.yeelightapp.businesslogic.manager

import com.example.yeelightapp.businesslogic.YeelightAPI
import com.example.yeelightapp.businesslogic.manager.interfaces.IManagerBL

class ManagerAPI() : IManagerBL {
    private val businessLogic = YeelightAPI()

    override suspend fun connect(ip: String):Boolean {
       return businessLogic.connect( ip)
    }


    override fun changeRGB(red: Int, green: Int, blue: Int) {
        if (red == 0 && green == 0 && blue == 0) {
            businessLogic.changeRGB(1, 1, 1)
        } else {
            businessLogic.changeRGB(red, green, blue)
        }
    }

    override fun changeBrightness(brightness: Int) {
        if (brightness == 0) {
            businessLogic.changeBrightness(1)
        } else {
            businessLogic.changeBrightness(brightness)
        }
    }

    override fun turnOn() {
        businessLogic.turnOn()
    }

    override fun turnOff() {
        businessLogic.turnOff()
    }

    override suspend fun setCurrentRGBB():List<Any> {
       return businessLogic.setCurrentRGBB()
    }
}