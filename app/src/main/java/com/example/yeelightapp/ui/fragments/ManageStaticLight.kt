package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class ManageStaticLight : Fragment() {
    private val viewModel: LampViewModel by inject()
    private var ip: String = this.arguments?.getString("IP").toString()
    private val args = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_static, container, false)
    }

    override fun onResume() {
        super.onResume()
        val red: SeekBar = requireView().findViewById(R.id.red2)
        val green: SeekBar = requireView().findViewById(R.id.green2)
        val blue: SeekBar = requireView().findViewById(R.id.blue2)
        val brightness: SeekBar = requireView().findViewById(R.id.brightness2)
        val onOff: ToggleButton = requireView().findViewById(R.id.onOff2)
        val res = viewModel.setCurrentRGBB(ip)
        val navigationBottom: BottomNavigationView =
            requireView().findViewById(R.id.bottomNavigation)
        navigationBottom.selectedItemId = R.id.action_light
        navigationBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_mode -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_static1_to_modes, args)
                }
                R.id.action_about -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_static1_to_aboutPage, args)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        res.observe(this, { list ->
            red.progress = list.red
            green.progress = list.green
            blue.progress = list.blue
            brightness.progress = list.bright
            onOff.setOnCheckedChangeListener(null)
            onOff.isChecked = (list.power) == "on"
            onOff.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.turnOn()
                } else {
                    viewModel.turnOff()
                }
            }
        })
        red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
            }
        })
        green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
            }
        })
        blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeRGB(red.progress, green.progress, blue.progress)
            }
        })
        brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeBrightness(brightness.progress)
            }
        })
    }

}