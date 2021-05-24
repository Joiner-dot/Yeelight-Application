package com.example.yeelightapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.RestartTools
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.getScopeId
import org.koin.core.component.inject
import java.lang.Exception
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class YeelightApiTest {

    private val ip = "192.168.1.45"

    private val fakeIp = "14.08.2000"

    private val testPropertyForUI = PropertyForUI(255, 173, 0, 3, "off")

    private val yeelightAPIImpl = YeelightAPIImpl(Gson())

    @Test
    fun connectionTest() {
        runBlocking {
            launch(Dispatchers.Default) {
                yeelightAPIImpl.connect(ip)
            }
        }
    }

    @Test(expected = SocketTimeoutException::class)
    fun connectionFakeIpTest() {
        runBlocking {
            launch(Dispatchers.Default) {
                yeelightAPIImpl.connect(fakeIp)
            }
        }
    }

    @Test
    fun getRGBActual() {
        connectionTest()
        runBlocking {
            launch(Dispatchers.Default) {
                yeelightAPIImpl.turnOn()
                delay(1000)
                yeelightAPIImpl.changeRGB(255, 173, 0)
                delay(1000)
                yeelightAPIImpl.turnOff()
                delay(1000)
                val result = yeelightAPIImpl.setCurrentRGBB(ip)
                assertEquals(testPropertyForUI, result)
            }
        }
    }
}