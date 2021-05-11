package com.example.yeelightapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import com.example.yeelightapp.R
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.koin.android.ext.android.inject

class Modes : Fragment() {

    private val lampViewModel: LampViewModel by inject()
    private lateinit var ip: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modes_page, container, false)
        val bundle = this.arguments
        ip = bundle?.getString("IP").toString()
        return view
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onResume() {
        super.onResume()
        val onOff:Switch by lazy { requireView().findViewById(R.id.onOffMode) }
        val res = lampViewModel.setCurrentRGBB(ip)
        res.observe(this, { list ->
            onOff.setOnCheckedChangeListener(null)
            onOff.isChecked = (list[4].toString()) == "on"
            onOff.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lampViewModel.turnOn()
                } else {
                    lampViewModel.turnOff()
                }
            }
        })
        val nightMode: Button by lazy { requireView().findViewById(R.id.nightMode) }
        val workMode: Button by lazy { requireView().findViewById(R.id.workMode) }
        val partyMode: Button by lazy { requireView().findViewById(R.id.partyMode) }
        val romanticMode: Button by lazy { requireView().findViewById(R.id.romanticMode) }
        nightMode.setOnClickListener {
            lampViewModel.nightMode()
        }
        workMode.setOnClickListener {
            lampViewModel.workMode()
        }
        partyMode.setOnClickListener {
            lampViewModel.partyMode()
        }
        romanticMode.setOnClickListener {
            lampViewModel.romanticMode()
        }
    }
}