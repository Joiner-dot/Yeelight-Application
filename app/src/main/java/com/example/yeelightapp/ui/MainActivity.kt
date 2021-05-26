package com.example.yeelightapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.yeelightapp.R
import com.jakewharton.processphoenix.ProcessPhoenix


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return;
        }
        supportActionBar!!.hide()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}