package com.example.yeelightapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.yeelightapp.R
import com.example.yeelightapp.di.appModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide();
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startKoin {
            modules(appModule)
        }
    }

    override fun onDestroy() {
        stopKoin()
        super.onDestroy()
    }
}