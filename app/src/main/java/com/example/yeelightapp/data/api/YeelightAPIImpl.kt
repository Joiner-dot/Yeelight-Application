package com.example.yeelightapp.data.api

import android.graphics.Color
import android.util.Log
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.lamps.Property
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

    override suspend fun connect(ip: String): Boolean {
        try {
            val mSocket = Socket()
            mSocket.connect(InetSocketAddress(ip, 55443), 2000)
            if (!mSocket.isConnected) {
                throw SocketTimeoutException()
            }
            mSocket.keepAlive = true
            mBos = BufferedOutputStream(mSocket.getOutputStream())
            mReader = BufferedReader(InputStreamReader(mSocket!!.getInputStream()))
        } catch (e: SocketTimeoutException) {
            Log.d("Socket", e.printStackTrace().toString())
            return false
        } catch (e: SocketException) {
            connect(ip)
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
            return false
        }
        return true
    }

    override suspend fun changeRGB(red: Int, green: Int, blue: Int) {
        try {
            val color = (red * 65536) + (green * 256) + blue
            val newVal =
                "{\"id\":2,\"method\":\"set_rgb\",\"params\":[$color, \"smooth\", 500]}\r\n"
            mBos.write(newVal.toByteArray())
            mBos.flush()
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    override suspend fun changeBrightness(brightness: Int) {
        try {
            mBos.write(("{\"id\":1,\"method\":\"set_bright\",\"params\":[$brightness, \"smooth\", 500]}\r\n").toByteArray())
            mBos.flush()
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    override suspend fun turnOn() {
        try {
            mBos.write(("{\"id\":1,\"method\":\"set_power\",\"params\":[\"on\",\"smooth\",500]}\r\n").toByteArray())
            mBos.flush()
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    override suspend fun turnOff() {

        try {
            mBos.write(("{\"id\":11,\"method\":\"set_power\",\"params\":[\"off\",\"smooth\",500]}\r\n").toByteArray())
            mBos.flush()
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }
    }

    override suspend fun nightMode() {
        mBos.write(("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"5000,1,16755200,1,5000,1,16744960,1\"]}\r\n").toByteArray())
        mBos.flush()
    }

    override suspend fun workMode() {
        mBos.write(("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"5000,1,16777215,60,15000,1,16760480,40\"]}\r\n").toByteArray())
        mBos.flush()
    }

    override suspend fun partyMode() {
        mBos.write(("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"2000,1,16711680,80,2000,1,16755200,80,2000," +
                "1,65280,80,2000,1,65535,80,2000,1,16711935,80,2000,1,255,80\"]}\r\n").toByteArray())
        mBos.flush()
    }

    override suspend fun romanticMode() {
        mBos.write(("{\"id\":1, \"method\":\"set_scene\",\"params\":[\"cf\",0,0,\"2000,1,16711870,60,800,1,11141375,40\"]}\r\n").toByteArray())
        mBos.flush()
    }

    override suspend fun setCurrentRGBB(ip: String): List<Any> {
        var value: String?
        var currentLine: String
        try {
            while (true) {
                try {
                    mBos.write("{\"id\":5,\"method\":\"get_prop\",\"params\":[\"power\", \"rgb\", \"bright\"]}\r\n".toByteArray())
                    mBos.flush()
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
                } catch (e: Exception) {
                    connect(ip)
                }
            }
            if (value.toString() == "null") {
                throw NullPointerException()
            }
            val list = arrayListOf<Any>()
            val gson = Gson().fromJson(value, Property::class.java)
            list.add(Color.red(gson.result[1].toInt()))
            list.add(Color.green(gson.result[1].toInt()))
            list.add(Color.blue(gson.result[1].toInt()))
            list.add(gson.result[2].toInt())
            list.add(gson.result[0])
            return list
        } catch (e: Exception) {
            Log.d("Exception", e.printStackTrace().toString())
        }
        return (arrayListOf(0, 0, 0, 0, "off"))
    }
}