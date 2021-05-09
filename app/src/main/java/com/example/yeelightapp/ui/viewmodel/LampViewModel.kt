package com.example.yeelightapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.datasource.room.LampDataBase
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.lamps.LampDst
import com.example.yeelightapp.mapper.LampMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LampViewModel(application: Application) : ViewModel() {

    val readAllData: LiveData<List<LampDst>>
    private val repositoryImpl: LampRepository
    private val lampMapper: LampMapper = LampMapper()

    init {
        val lampDao = LampDataBase.getDatabase(application).lammpDAO()
        repositoryImpl = LampRepositoryImpl(lampDao, YeelightAPIImpl())
        readAllData = lampMapper.transform(repositoryImpl.readAllData)
    }

    fun addLamp(lamp: LampDst) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addLamp(lampMapper.reverseTransform(lamp))
        }
    }

    fun deleteLamp(lamp: LampDst) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteLamp(lampMapper.reverseTransform(lamp))
        }
    }

    fun connect(ip: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repositoryImpl.connect(ip))
        }
        return result
    }

    fun setCurrentRGBB(): LiveData<List<Any>> {
        val result = MutableLiveData<List<Any>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repositoryImpl.setCurrentRGBB())
        }
        return result
    }

    fun changeRGB(red: Int, green: Int, blue: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.changeRGB(red, green, blue)
        }
    }

    fun changeBrightness(brightness: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.changeBrightness(brightness)
        }
    }

    fun turnOn() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.turnOn()
        }
    }

    fun turnOff() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.turnOff()
        }
    }
}