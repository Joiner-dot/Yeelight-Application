package com.example.yeelightapp.database.interfaces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LampViewModel(application: Application):AndroidViewModel(application) {

    val readAllData:LiveData<List<Lamp>>
    private val repository:LampRepository

    init {
        val lampDao = LampDataBase.getDatabase(application).iDataBase()
        repository = LampRepository(lampDao)
        readAllData = repository.readAllData
    }

    fun addLamp(lamp: Lamp){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLamp(lamp)
        }
    }

    fun deleteLamp(lamp: Lamp){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLamp(lamp)
        }
    }
}