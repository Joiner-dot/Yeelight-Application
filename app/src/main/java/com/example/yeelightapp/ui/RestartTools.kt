package com.example.yeelightapp.ui

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


open class RestartTools(private val context: Context) {

    suspend fun restartApp() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                context,
                "Restarting Application",
                Toast.LENGTH_SHORT
            ).show()
        }
        delay(1000)
        val intent = Intent(context, MainActivity::class.java)
        ProcessPhoenix.triggerRebirth(context, intent)
    }
}