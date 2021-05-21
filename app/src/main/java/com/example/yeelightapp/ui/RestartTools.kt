package com.example.yeelightapp.ui

import android.content.Context
import com.jakewharton.processphoenix.ProcessPhoenix

class RestartTools(private val context: Context) {

    fun restartApp(){
        ProcessPhoenix.triggerRebirth(context)
    }
}