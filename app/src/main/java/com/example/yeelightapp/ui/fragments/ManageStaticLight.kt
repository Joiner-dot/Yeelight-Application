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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_static, container, false)
    }

    override fun onResume() {
        super.onResume()

        val ip: String = requireActivity().intent.getStringExtra("IP").toString()

        val red: SeekBar = requireView().findViewById(R.id.red2)

        val green: SeekBar = requireView().findViewById(R.id.green2)

        val blue: SeekBar = requireView().findViewById(R.id.blue2)

        val brightness: SeekBar = requireView().findViewById(R.id.brightness2)

        val onOff: ToggleButton = requireActivity().findViewById(R.id.onOff2)

        val navigationBottom: BottomNavigationView =
            requireActivity().findViewById(R.id.navigationMode)

        val res = viewModel.setCurrentRGBB(ip)


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

        navigationBottom.apply {
            setOnNavigationItemSelectedListener {
                return@setOnNavigationItemSelectedListener true
            }
            setBackgroundResource(R.color.static_manage)
            selectedItemId = R.id.action_light
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.action_mode -> {
                        findNavController().navigate(
                            R.id.action_static1_to_modes
                        )
                    }
                    R.id.action_about -> {
                        findNavController().navigate(
                            R.id.action_static1_to_aboutPage
                        )
                    }
                }
                return@setOnNavigationItemSelectedListener true
            }
        }

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