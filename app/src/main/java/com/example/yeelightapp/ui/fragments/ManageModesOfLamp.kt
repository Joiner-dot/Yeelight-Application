package com.example.yeelightapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class ManageModesOfLamp : Fragment() {

    private val lampViewModel: LampViewModel by inject()
    private val ip: String = this.arguments?.getString("IP").toString()
    private val args = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modes_page, container, false)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onResume() {
        super.onResume()
        val onOff: Switch by lazy { requireView().findViewById(R.id.onOffMode) }
        val res = lampViewModel.setCurrentRGBB(ip)
        val nightMode: Button = requireView().findViewById(R.id.nightMode)
        val workMode: Button = requireView().findViewById(R.id.workMode)
        val partyMode: Button = requireView().findViewById(R.id.partyMode)
        val romanticMode: Button = requireView().findViewById(R.id.romanticMode)
        val navigationBottom: BottomNavigationView = requireView().findViewById(R.id.navigationMode)
        navigationBottom.selectedItemId = R.id.action_mode
        navigationBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_light -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_modes_to_static1, args)
                }
                R.id.action_about -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_modes_to_aboutPage, args)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        res.observe(this, { list ->
            turnSwitch(onOff, (list.power) == "on")
        })
        nightMode.setOnClickListener {
            lampViewModel.turnMode("Night")
            turnSwitch(onOff, true)
        }
        workMode.setOnClickListener {
            lampViewModel.turnMode("Work")
            turnSwitch(onOff, true)
        }
        partyMode.setOnClickListener {
            lampViewModel.turnMode("Party")
            turnSwitch(onOff, true)
        }
        romanticMode.setOnClickListener {
            lampViewModel.turnMode("Romantic")
            turnSwitch(onOff, true)
        }
        onOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                lampViewModel.turnOn()
            } else {
                lampViewModel.turnOff()
            }
        }
    }

    private fun turnSwitch(onOff: Switch, value: Boolean) {
        if (!onOff.isChecked && value) {
            onOff.setOnCheckedChangeListener(null)
            onOff.isChecked = value
            onOff.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lampViewModel.turnOn()
                } else {
                    lampViewModel.turnOff()
                }
            }
        }
    }
}