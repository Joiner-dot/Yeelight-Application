package com.example.yeelightapp.data.api

import android.graphics.Color
import android.util.Log
import com.example.yeelightapp.data.api.enums.*
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.lamps.Properties
import com.example.yeelightapp.lamps.SetCommand
import com.google.gson.Gson
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException


class YeelightAPIImpl(private val gson: Gson) : YeelightAPI {

    private lateinit var mBos: BufferedOutputStream

    private lateinit var mReader: BufferedReader

    override suspend fun connect(ip: String) {
        val mSocket = Socket()
        mSocket.connect(InetSocketAddress(ip, 55443), 2000)
        if (!mSocket.isConnected) {
            throw SocketTimeoutException()
        }
        mSocket.keepAlive = true
        mBos = BufferedOutputStream(mSocket.getOutputStream())
        mReader = BufferedReader(InputStreamReader(mSocket!!.getInputStream()))
    }

    override suspend fun changeRGB(red: Int, green: Int, blue: Int) {
        val color = (red * KoefsForColor.Red.color) + (green * KoefsForColor.Green.color) + blue
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetRGB.command,
                listOf(color, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        printToTheLamp(jsonString)
    }

    override suspend fun changeBrightness(brightness: Int) {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetBright.command,
                listOf(brightness, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        printToTheLamp(jsonString)
    }

    override suspend fun turnOn() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetPower.command,
                listOf(Power.On.property, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        printToTheLamp(jsonString)
    }

    override suspend fun turnOff() {
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.SetPower.command,
                listOf(Power.Off.property, "smooth", 500)
            )
        ) + Tools.NextLine.tool
        printToTheLamp(jsonString)

    }

    override suspend fun nightMode() {
        printToTheLamp(Modes.Night.command)

    }

    override suspend fun workMode() {
        printToTheLamp(Modes.Work.command)
    }

    override suspend fun partyMode() {
        printToTheLamp(Modes.Party.command)
    }

    override suspend fun romanticMode() {
        Log.d("HHEHEH", Modes.Romantic.command)
        printToTheLamp(Modes.Romantic.command)
    }

    override suspend fun setCurrentRGBB(ip: String): PropertyForUI {
        val value: String?
        var currentLine: String
        val propertyForUI: PropertyForUI
        val jsonString = gson.toJson(
            SetCommand(
                1,
                Commands.GetProperties.command,
                listOf("power", "rgb", "bright")
            )
        ) + Tools.NextLine.tool
        while (true) {
            printToTheLamp(jsonString)
            while (true) {
                currentLine = mReader.readLine()
                if (currentLine.contains("result")
                    && (currentLine.contains(Power.On.property)
                            || currentLine.contains(Power.Off.property))
                ) {
                    value = currentLine
                    break
                }
            }
            break
        }
        val properties: Properties = Gson().fromJson(value, Properties::class.java)
        propertyForUI = PropertyForUI(
            Color.red(properties.result[1].toInt()),
            Color.green(properties.result[1].toInt()),
            Color.blue(properties.result[1].toInt()),
            properties.result[2].toInt(),
            properties.result[0]
        )
        return propertyForUI
    }

    private fun printToTheLamp(command: String) {
        mBos.apply {
            write((command).toByteArray())
            flush()
        }
    }
}
