package com.example.yeelightapp.activities

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import android.widget.ToggleButton
import com.example.yeelightapp.R
import com.example.yeelightapp.businesslogic.manager.ManagerAPI
import kotlinx.coroutines.*


class MenuPage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
    }

    override fun onResume() {
        super.onResume()
        val ip = intent.getSerializableExtra("IP")
        val managerBL = ManagerAPI()
        val red: SeekBar = findViewById(R.id.red)
        val green: SeekBar = findViewById(R.id.green)
        val blue: SeekBar = findViewById(R.id.blue)
        val brightness: SeekBar = findViewById(R.id.brightness)
        val onOff: ToggleButton = findViewById(R.id.onOff)
        var value: String? = null
        runBlocking {
            launch(Dispatchers.IO) {
                val e = managerBL.connect(ip.toString())
                if (!e) {
                    this@MenuPage.finish()
                } else {
                    val list = managerBL.setCurrentRGBB()
                    red.progress = list[0] as Int
                    green.progress = list[1] as Int
                    blue.progress = list[2] as Int
                    brightness.progress = list[3] as Int
                    value = list[4].toString()
                }
            }
        }
        Handler().post {
            onOff.setOnCheckedChangeListener(null)
            onOff.isChecked = (value) == "on"
            onOff.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    managerBL.turnOn()
                } else {
                    managerBL.turnOff()
                }
            }
        }
        red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                managerBL.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Red")
            }
        })
        green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                managerBL.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Green")
            }
        })
        blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                managerBL.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Blue")
            }
        })
        brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                managerBL.changeBrightness(brightness.progress)
                Log.d("TAGGG", "Brightness")
            }
        })
    }
}