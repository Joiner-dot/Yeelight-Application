package com.example.yeelightapp.di

import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module() {
    single { LampViewModel(get()) }
}
