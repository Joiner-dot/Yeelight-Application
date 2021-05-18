package com.example.yeelightapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yeelightapp.R
import com.example.yeelightapp.data.api.enums.Modes
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageModesOfLamp : Fragment() {

    private val lampViewModel: LampViewModel by inject()
    private val args: ManageModesOfLampArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modes_page, container, false)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()

        val onOff: Switch = requireView().findViewById(R.id.onOffMode)

        val ip = args.IP

        val res = lampViewModel.setCurrentRGBB(ip)

        val nightMode: ImageButton = requireView().findViewById(R.id.nightMode)

        val workMode: ImageButton = requireView().findViewById(R.id.workMode)

        val partyMode: ImageButton = requireView().findViewById(R.id.partyMode)

        val romanticMode: ImageButton = requireView().findViewById(R.id.romanticMode)

        val navigationBottom: BottomNavigationView = requireView().findViewById(R.id.navigationMode)


        navigationBottom.selectedItemId = R.id.action_mode
        navigationBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_light -> {
                    findNavController().navigate(
                        ManageModesOfLampDirections.actionModesToStatic1(ip)
                    )
                }
                R.id.action_about -> {
                    findNavController().navigate(
                        ManageModesOfLampDirections.actionModesToAboutPage(ip)
                    )
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        res.observe(this, { list ->
            turnSwitch(onOff, (list.power) == "on")
        })
        nightMode.setOnClickListener {
            lampViewModel.turnMode(Modes.Night)
            turnSwitch(onOff, true)
        }
        workMode.setOnClickListener {
            lampViewModel.turnMode(Modes.Work)
            turnSwitch(onOff, true)
        }
        partyMode.setOnClickListener {
            lampViewModel.turnMode(Modes.Party)
            turnSwitch(onOff, true)
        }
        romanticMode.setOnClickListener {
            lampViewModel.turnMode(Modes.Romantic)
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
        onOff.apply {
            if (!this.isChecked && value) {
                setOnCheckedChangeListener(null)
                isChecked = value
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        lampViewModel.turnOn()
                    } else {
                        lampViewModel.turnOff()
                    }
                }
            }
        }
    }
}