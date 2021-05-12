package com.example.yeelightapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.datasource.room.LampDataBase
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.lamps.LampForUI
import com.example.yeelightapp.lamps.PropertyForUI
import com.example.yeelightapp.mapper.LampMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException

class LampViewModel(application: Application) : ViewModel() {

    val readAllData: LiveData<ArrayList<LampForUI>>
    private val repositoryImpl: LampRepository
    private val lampMapper: LampMapper = LampMapper()

    init {
        val lampDao = LampDataBase.getDatabase(application).lampDAO()
        repositoryImpl = LampRepositoryImpl(lampDao, YeelightAPIImpl())
        readAllData = lampMapper.transform(repositoryImpl.readAllData)
    }


    fun addLamp(lamp: LampForUI) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.addLamp(lampMapper.reverseTransform(lamp))
        }
    }

    fun turnMode(mode:String) {
        viewModelScope.launch(Dispatchers.Default) {
            repositoryImpl.turnMode(mode)
        }
    }

    fun deleteLamp(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.deleteLamp(id)
        }
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