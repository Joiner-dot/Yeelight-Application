package com.example.yeelightapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.di.koincomponents.ViewModelNew
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.lamps.PropertyForUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException

class LampViewModel : ViewModelNew() {

    val readAllData: LiveData<List<LampUI>> = lampMapper.transform(repositoryImpl.readAllData)


    fun addLamp(lamp: LampUI) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addLamp(lampMapper.reverseTransform(lamp))
        }
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

    fun connect(ip: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.connect(ip)
                result.postValue(true)
            } catch (e: SocketTimeoutException) {
                Log.d("Socket", e.printStackTrace().toString())
                result.postValue(false)
            } catch (e: SocketException) {
                connect(ip)
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
                result.postValue(false)
            }
        }
        return result
    }

    fun setCurrentRGBB(ip: String): LiveData<PropertyForUI> {
        val result = MutableLiveData<PropertyForUI>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repositoryImpl.setCurrentRGBB(ip))
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
