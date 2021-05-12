package com.example.yeelightapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampForUI
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.koin.android.ext.android.inject

class AddLamp : Fragment() {

    private val mLampViewModel: LampViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_lamp, container, false)

        val select: Button = view.findViewById(R.id.select)

        select.setOnClickListener {
            insertDataToDataBase()
        }

        return view
    }


    private fun insertDataToDataBase() {
        val name = view?.findViewById<TextView>(R.id.nameOfNewLamp)?.text
        val ip = view?.findViewById<TextView>(R.id.idNewLamp)?.text

        if (requireActivity().currentFocus != null) {
            val inputManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        if (inputCheck(name.toString(), ip.toString())) {
            val lamp = LampForUI(0, name.toString(), ip.toString())
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