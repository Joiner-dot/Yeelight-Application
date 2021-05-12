package com.example.yeelightapp.data.api

import android.graphics.Color
import android.util.Log
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.lamps.PropertyFromCommand
import com.example.yeelightapp.lamps.SetCommandClass
import com.google.gson.Gson
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException


class YeelightAPIImpl : YeelightAPI {
    private lateinit var mBos: BufferedOutputStream
    private lateinit var mReader: BufferedReader
    private val gson = Gson()

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
        val color = (red * 65536) + (green * 256) + blue
        val jsonString = gson.toJson(
            SetCommandClass(
                1,
                "set_rgb",
                listOf(color, "smooth", 500)
            )
        ) + "\r\n"
        printToTheLamp(jsonString)
    }

    override suspend fun changeBrightness(brightness: Int) {
        val jsonString = gson.toJson(
            SetCommandClass(
                1,
                "set_bright",
                listOf(brightness, "smooth", 500)
            )
        ) + "\r\n"
        printToTheLamp(jsonString)
    }

    override suspend fun turnOn() {
        val jsonString = gson.toJson(
            SetCommandClass(
                1,
                "set_power",
                listOf("on", "smooth", 500)
            )
        ) + "\r\n"
        printToTheLamp(jsonString)
    }

    override suspend fun turnOff() {
        val jsonString = gson.toJson(
            SetCommandClass(
                1,
                "set_power",
                listOf("off", "smooth", 500)
            )
        ) + "\r\n"
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
        printToTheLamp(Modes.Romantic.command)

    }

    override suspend fun setCurrentRGBB(ip: String): PropertyForUI {
        val value: String?
        var currentLine: String
        val propertyFromCommand: PropertyFromCommand
        val propertyForUI: PropertyForUI
        val jsonString = gson.toJson(
            SetCommandClass(
                1,
                "get_prop",
                listOf("power", "rgb", "bright")
            )
        ) + "\r\n"
        while (true) {
            printToTheLamp(jsonString)
            while (true) {
                currentLine = mReader.readLine()
                if (currentLine.contains("result")
                    && (currentLine.contains("on")
                            || currentLine.contains("off"))
                ) {
                    value = currentLine
                    break
                }
            }
            break
        }
        propertyFromCommand = Gson().fromJson(value, PropertyFromCommand::class.java)
        propertyForUI = PropertyForUI(
            Color.red(propertyFromCommand.result[1].toInt()),
            Color.green(propertyFromCommand.result[1].toInt()),
            Color.blue(propertyFromCommand.result[1].toInt()),
            propertyFromCommand.result[2].toInt(),
            propertyFromCommand.result[0]
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