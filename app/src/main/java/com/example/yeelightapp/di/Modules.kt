package com.example.yeelightapp.di

import android.os.Bundle
import androidx.room.Room
import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.data.datasource.room.LampDataBase
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import java.net.Socket

val appModule: Module = module() {
    single { LampViewModel() }

    single { LampMapper() }

    single<YeelightAPI> { YeelightAPIImpl() }

    single <DataBase> {
        return@single Room.databaseBuilder(
            androidContext().applicationContext,
            LampDataBase::class.java,
            "lamps"
        ).build().lampDAO()
    }

    single { Bundle() }

    single { Gson() }


    single { LampRepositoryImpl(get(), get()) }
}
