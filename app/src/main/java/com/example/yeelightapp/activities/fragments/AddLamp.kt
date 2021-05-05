package com.example.yeelightapp.activities.fragments

import android.os.Bundle
import android.text.BoringLayout.make
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R
import com.example.yeelightapp.database.interfaces.Lamp
import com.example.yeelightapp.database.interfaces.LampViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

class AddLamp : Fragment() {

    private lateinit var mLampViewModel: LampViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.add_lamp, container, false)

        mLampViewModel = ViewModelProvider(this).get(LampViewModel::class.java)
        val select: Button = view.findViewById(R.id.select)

        select.setOnClickListener {
            insertDataToDataBase()
        }

        return view
    }

    private fun insertDataToDataBase() {
        val name = view?.findViewById<TextView>(R.id.nameOfNewLamp)?.text
        val ip = view?.findViewById<TextView>(R.id.idNewLamp)?.text

        if (inputCheck(name.toString(), ip.toString())) {
            val lamp = Lamp(0, name.toString(), ip.toString())
            mLampViewModel.addLamp(lamp)
            Toast.makeText(
                requireContext(),
                "New Lamp was added",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_addLamp_to_listFragments)
        } else {
            Toast.makeText(
                requireContext(),
                "Wrong data",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun inputCheck(name: String, ip: String): Boolean {

        return !(name == "" || ip == "" || ip.length <= 6)


    }

}