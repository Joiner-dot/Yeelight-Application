package com.example.yeelightapp.ui

import android.app.Application
import com.example.yeelightapp.di.appModule
import com.jakewharton.processphoenix.ProcessPhoenix
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application(){
    override fun onCreate() {
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return
        }
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}