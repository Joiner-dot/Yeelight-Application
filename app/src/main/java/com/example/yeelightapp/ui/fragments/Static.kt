package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yeelightapp.R
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

class Static : Fragment() {
    lateinit var ip: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_static, container, false)
        val bundle = this.arguments
        ip = bundle?.getString("IP").toString()
        return view
    }

    override fun onResume() {
        super.onResume()
        val red: SeekBar = requireView().findViewById(R.id.red2)
        val green: SeekBar = requireView().findViewById(R.id.green2)
        val blue: SeekBar = requireView().findViewById(R.id.blue2)
        val brightness: SeekBar = requireView().findViewById(R.id.brightness2)
        val onOff: ToggleButton = requireView().findViewById(R.id.onOff2)
        val viewModel: LampViewModel = get<LampViewModel>()
        val e = viewModel.connect(ip)
        e.observe(this, { returned ->
            if (!returned) {
                Toast.makeText(
                    requireContext(),
                    "Connection failed",
                    Toast.LENGTH_LONG
                ).show()
                this@Static.activity?.onBackPressed()
            } else {
                val res = viewModel.setCurrentRGBB()
                res.observe(this, { list ->
                    red.progress = list?.get(0) as Int
                    green.progress = list[1] as Int
                    blue.progress = list[2] as Int
                    brightness.progress = list[3] as Int
                    onOff.setOnCheckedChangeListener(null)
                    onOff.isChecked = (list[4].toString()) == "on"
                    onOff.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            viewModel.turnOn()
                        } else {
                            viewModel.turnOff()
                        }
                    }
                })
            }
        })
        red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Red")
            }
        })
        green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Green")
            }
        })
        blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
                Log.d("TAGGG", "Blue")
            }
        })
        brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeBrightness(brightness.progress)
                Log.d("TAGGG", "Brightness")
            }
        })
    }
}