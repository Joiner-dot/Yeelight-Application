package com.example.yeelightapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.yeelightapp.database.api.YeelightAPI
import com.example.yeelightapp.database.datasource.Lamp
import com.example.yeelightapp.database.datasource.LampDataBase
import com.example.yeelightapp.database.repository.LampRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LampViewModel(application: Application) : ViewModel() {

    val readAllData: LiveData<List<Lamp>>
    private val repository: LampRepository

    init {
        val lampDao = LampDataBase.getDatabase(application).lammpDAO()
        val yeelightAPI = YeelightAPI()
        repository = LampRepository(lampDao, yeelightAPI)
        readAllData = repository.readAllData
    }

    fun addLamp(lamp: Lamp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLamp(lamp)
        }
    }

    fun deleteLamp(lamp: Lamp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLamp(lamp)
        }
    }

    fun connect(ip: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.connect(ip))
        }
        return result
    }

    fun setCurrentRGBB(): LiveData<List<Any>> {
        val result = MutableLiveData<List<Any>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.setCurrentRGBB())
        }
        return result
    }

    fun changeRGB(red: Int, green: Int, blue: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeRGB(red, green, blue)
        }
    }

    fun changeBrightness(brightness: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeBrightness(brightness)
        }
    }

    fun turnOn() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.turnOn()
        }
    }

    fun turnOff() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.turnOff()
        }
    }
}