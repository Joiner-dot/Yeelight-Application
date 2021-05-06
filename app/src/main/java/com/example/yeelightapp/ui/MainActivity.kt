package com.example.yeelightapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yeelightapp.R
import com.example.yeelightapp.di.appModule
import org.koin.android.ext.android.startKoin


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startKoin(applicationContext, listOf(appModule))
    }
}