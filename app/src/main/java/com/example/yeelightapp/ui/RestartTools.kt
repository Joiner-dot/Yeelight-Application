package com.example.yeelightapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent

class RestartTools(private val context: Context) {

    fun restartApp(){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }
}