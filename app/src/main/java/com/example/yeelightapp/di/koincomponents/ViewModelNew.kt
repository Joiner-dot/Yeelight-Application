package com.example.yeelightapp.di.koincomponents

import androidx.lifecycle.ViewModel
import com.example.yeelightapp.data.datasource.room.LampDataBaseProvider
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.mapper.LampMapper
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

abstract class ViewModelNew : ViewModel(), KoinComponent {

    private val dataBaseProvider: LampDataBaseProvider by inject()

    val repositoryImpl: LampRepository by inject<LampRepositoryImpl> {
        parametersOf(
            dataBaseProvider.instance.lampDAO()
        )
    }

    val lampMapper: LampMapper by inject()
}
