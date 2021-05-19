package com.example.yeelightapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.mapper.LampMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException

class LampViewModel(
    private val repositoryImpl: LampRepository,
    private val lampMapper: LampMapper,
) : ViewModel() {

    val readAllData: LiveData<List<LampUI>> = lampMapper.transform(repositoryImpl.readAllData)

    fun addLamp(lamp: LampUI): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.addLamp(lampMapper.reverseTransform(lamp))
                result.postValue(true)
            } catch (e: Exception) {
                result.postValue(false)
            }
        }
        return result
    }

    fun turnMode(mode: Modes) {
        viewModelScope.launch(Dispatchers.Default) {
            repositoryImpl.turnMode(mode)
        }
    }

    fun deleteLamp(lamp: LampUI): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.deleteLamp(lamp.id)
                result.postValue(true)
            } catch (e: Exception) {
                result.postValue(false)
            }
        }
        return result
    }

    fun connect(ip: String, tryFlag: Int): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                repositoryImpl.connect(ip)
                result.postValue(true)
            } catch (e: SocketTimeoutException) {
                Log.d("Socket", e.printStackTrace().toString())
                result.postValue(false)
            } catch (e: SocketException) {
                result.postValue(false)
            } catch (e: Exception) {
                result.postValue(false)
            }
        }
        return result
    }

    fun setCurrentRGBB(ip: String, tryFlag: Int): MutableLiveData<PropertyForUI> {
        var result = MutableLiveData<PropertyForUI>()
        viewModelScope.launch(Dispatchers.Default) {
            try {
                Log.d("lll", tryFlag.toString())
                Log.d("lll", ip)
                result.postValue(repositoryImpl.setCurrentRGBB(ip))
            } catch (e: Exception) {
                if (tryFlag < 1) {
                    val newVal = tryFlag + 1
                    connect(ip, tryFlag)
                    delay(1000)
                    result = setCurrentRGBB(ip, newVal)
                } else {
                    result.postValue(PropertyForUI(0, 0, 0, 0, "off"))
                    Log.d("Exception", e.printStackTrace().toString())
                }
            }
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
