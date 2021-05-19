package com.example.yeelightapp.data.api

import android.graphics.Color
import android.util.Log
import com.example.yeelightapp.data.api.enums.*
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.lamps.Properties
import com.example.yeelightapp.lamps.SetCommand
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException


class YeelightAPIImpl(private val gson: Gson) : YeelightAPI {

    private lateinit var mBos: BufferedOutputStream

    private lateinit var mReader: BufferedReader

    private val connections = arrayListOf<Socket>()

    override suspend fun connect(ip: String) {

        var index: Int = isOnConnection(ip)

        if (index == -1) {
            val mSocket = Socket()
            mSocket.connect(InetSocketAddress(ip, 55443), 2000)
            connections.add(mSocket)
            index = connections.lastIndex
        }

        connections[index].keepAlive = true
        mBos = BufferedOutputStream(connections[index].getOutputStream())
        mReader = BufferedReader(InputStreamReader(connections[index]!!.getInputStream()))
        Log.d("JDJJD", connections.size.toString())
    }

    private fun isOnConnection(ip: String): Int {
        if (connections.isEmpty()) {
            return -1
        }
        for (i in 0 until connections.size) {
            Log.d("INET", connections[i].inetAddress.toString())
            if (connections[i].inetAddress.toString() == "/$ip") {
                return i
            }
        }
        return -1
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
                Log.d("LDLDLD", currentLine)
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

    override suspend fun closeConnection() {
        mReader.close()
        mBos.close()
    }

    private fun printToTheLamp(command: String) {
        mBos.apply {
            write((command).toByteArray())
            flush()
        }
    }
}
