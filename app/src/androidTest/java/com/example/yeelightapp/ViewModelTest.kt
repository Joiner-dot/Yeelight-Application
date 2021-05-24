package com.example.yeelightapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.datasource.room.LampDataBaseProvider
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.RestartTools
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ViewModelTest {

    private val ip = "192.168.1.45"

    private val fakeIp = "14.08.2000"

    private val testPropertyForUI = PropertyForUI(255, 255, 255, 3, "off")

    private val lampRepositoryImpl = LampRepositoryImpl(
        LampDataBaseProvider(
            InstrumentationRegistry
                .getInstrumentation().targetContext
        )
            .appDataBase.lampDAO(),
        YeelightAPIImpl(Gson())
    )

    private val viewModel: LampViewModel = LampViewModel(
        lampRepositoryImpl,
        LampMapper(),
        RestartTools(
            InstrumentationRegistry
                .getInstrumentation().targetContext
        )
    )


    @Test
    fun viewModelConnectionTest() {
        val result = viewModel.connect(ip, 0)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertEquals(true, value)
                }
            }
        }
    }

    @Test
    fun viewModelConnectionFakeIpTest() {
        val result = viewModel.connect(fakeIp, 0)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertEquals(false, value)
                }
            }
        }
    }

    @Test
    fun getActualRGB() {
        runBlocking {
            viewModelConnectionTest()
            delay(500)
            viewModel.turnOn()
            delay(500)
            viewModel.changeRGB(255, 255, 255)
            delay(500)
            viewModel.turnOff()
            delay(500)
            val result = viewModel.setCurrentRGBB(ip, 0)
            delay(500)
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertEquals(testPropertyForUI, value)
                }
            }
        }
    }
}