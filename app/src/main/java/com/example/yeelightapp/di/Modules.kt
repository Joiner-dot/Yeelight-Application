package com.example.yeelightapp.di

import com.example.yeelightapp.data.api.YeelightAPIImpl
import com.example.yeelightapp.data.api.interfaces.YeelightAPI
import com.example.yeelightapp.data.dao.LampStorage
import com.example.yeelightapp.data.datasource.room.AppDataBase
import com.example.yeelightapp.data.datasource.room.LampDataBaseProvider
import com.example.yeelightapp.data.repository.LampRepositoryImpl
import com.example.yeelightapp.data.repository.interfaces.LampRepository
import com.example.yeelightapp.mapper.LampMapper
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {

    single { LampMapper() }

    single { Gson() }

    single<YeelightAPI> { YeelightAPIImpl(get()) }

    single { LampViewModel(get(), get(), androidContext()) }

    single { LampDataBaseProvider(get()) }

    single { get<LampDataBaseProvider>().appDataBase }

    single<LampStorage> { get<AppDataBase>().lampDAO() }

    single<LampRepository> { LampRepositoryImpl(get(), get()) }
}
