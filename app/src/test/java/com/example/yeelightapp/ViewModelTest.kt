package com.example.yeelightapp

import androidx.lifecycle.MutableLiveData
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.RestartTools
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*


class ViewModelTest {

    private val repository = mock(LampRepositoryImpl::class.java)

    private val mapper = LampMapper()

    private val restartTools = mock(RestartTools::class.java)

    private val viewModel = LampViewModel(repository, mapper, restartTools)

    @Test
    fun connectTest() {
        val result = viewModel.connect("14.08.2000")
        assertEquals(MutableLiveData<Boolean>()::class.java, result::class.java)
    }

    @Test
    fun changeRGBTest() {
        val result = viewModel.changeRGB(255, 255, 255)
        assertEquals(true, result)
    }

    @Test
    fun changeBrightTest() {
        val result = viewModel.changeBrightness(99)
        assertEquals(true, result)
    }

    @Test
    fun turnOffTest() {
        val result = viewModel.turnOff()
        assertEquals(true, result)
    }

    @Test
    fun turnOnTest() {
        val result = viewModel.turnOn()
        assertEquals(true, result)
    }

    @Test
    fun setCurrentRGGB() {
        val result = viewModel.setCurrentRGBB("14.08.2000")
        assertEquals(MutableLiveData<PropertyForUI>()::class.java, result::class.java)
    }

    @Test
    fun closeConnectionTest() {
        val result = viewModel.closeConnection()
        assertEquals(true, result)
    }
}