package com.example.yeelightapp

import com.example.yeelightapp.data.api.enums.Commands
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.data.api.enums.Power
import com.example.yeelightapp.data.api.enums.Tools
import com.example.yeelightapp.lamps.SetCommand
import com.google.gson.Gson
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class YeelightApiTest {

    private val gson = Gson()

    private val turnOffCommand =
        """{"id":1,"method":"set_power","params":["off","smooth",500]}""" + "\r\n"

    private val turnOnCommand =
        """{"id":1,"method":"set_power","params":["on","smooth",500]}""" + "\r\n"

    private val changeRGBCommand =
        """{"id":1,"method":"set_rgb","params":[2555,"smooth",500]}""" + "\r\n"

    private val changeBrightnessCommand =
        """{"id":1,"method":"set_bright","params":[99,"smooth",500]}""" + "\r\n"

    private val nightModeCommand =
        """{"id":1, "method":"set_scene","params":["cf",0,0,"5000,1,16755200,1,5000,1,16744960,1"]}""" + "\r\n"

    private val partyModeCommand =
        """{"id":1, "method":"set_scene","params":["cf",0,0,"2000,1,16711680,80,2000,1,16755200,80,2000,1,65280,80,2000,1,65535,80,2000,1,16711935,80,2000,1,255,80"]}""" + "\r\n"

    private val workModeCommand =
        """{"id":1, "method":"set_scene","params":["cf",0,0,"5000,1,16777215,60,15000,1,16760480,40"]}""" + "\r\n"

    private val romanticModeCommand =
        """{"id":1, "method":"set_scene","params":["cf",0,0,"2000,1,16711870,60,800,1,11141375,40"]}""" + "\r\n"


    @Test
    fun turnOffTestCommand() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetPower.command,
                listOf(Power.Off.property, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        assertEquals(turnOffCommand, jsonString)
    }

    @Test
    fun turnOnTestCommand() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetPower.command,
                listOf(Power.On.property, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        assertEquals(turnOnCommand, jsonString)
    }

    @Test
    fun changeRGBTestCommand() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetRGB.command,
                listOf(2555, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        assertEquals(changeRGBCommand, jsonString)
    }

    @Test
    fun changeBrightnessTestCommand() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetBright.command,
                listOf(99, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        assertEquals(changeBrightnessCommand, jsonString)
    }

    @Test
    fun modesTest() {
        assertEquals(nightModeCommand, Modes.Night.command)
        assertEquals(partyModeCommand, Modes.Party.command)
        assertEquals(romanticModeCommand, Modes.Romantic.command)
        assertEquals(workModeCommand, Modes.Work.command)
    }
}