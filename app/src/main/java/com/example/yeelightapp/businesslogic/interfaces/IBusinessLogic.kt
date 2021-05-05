package com.example.yeelightapp.businesslogic.interfaces

import android.app.Activity
import android.widget.SeekBar
import android.widget.Switch
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import com.mollin.yapi.YeelightDevice

interface IBusinessLogic {

    suspend fun connect(ip: String):Boolean

    fun changeRGB(red: Int, green: Int, blue: Int)

    fun changeBrightness(brightness: Int)

    fun turnOn()

    fun turnOff()

    suspend fun setCurrentRGBB():List<Any>
}