package com.example.yeelightapp.ui

import android.content.Context
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.coroutines.delay

class RestartTools(private val context: Context) {

    suspend fun restartApp() {
        Toast.makeText(
            context,
            "Restarting Application",
            Toast.LENGTH_SHORT
        ).show()
        delay(1000)
        ProcessPhoenix.triggerRebirth(context)
    }
}