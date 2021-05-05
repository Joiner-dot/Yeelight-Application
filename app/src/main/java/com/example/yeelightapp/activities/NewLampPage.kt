package com.example.yeelightapp.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.yeelightapp.R
import com.example.yeelightapp.database.interfaces.LampViewModel
import com.google.android.material.snackbar.Snackbar

class NewLampPage : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name: EditText = findViewById(R.id.nameOfNewLamp)
        val ip: EditText = findViewById(R.id.idNewLamp)
        val select: Button = findViewById(R.id.select)

        select.setOnClickListener {





//            if (true) {
//                Snackbar.make(
//                    findViewById(R.id.newLamp),
//                    "New Lamp was added",
//                    Snackbar.LENGTH_LONG
//                ).show()
//            } else {
//                Snackbar.make(
//                    findViewById(R.id.newLamp),
//                    "Something went wrong",
//                    Snackbar.LENGTH_LONG
//                ).show()
//            }
        }
    }
}