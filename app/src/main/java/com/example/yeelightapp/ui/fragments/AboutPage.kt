package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yeelightapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class AboutPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_about_page, container, false)

        val navigationBottom: BottomNavigationView =
            requireActivity().findViewById(R.id.navigationMode)


        navigationBottom.apply {
            setOnNavigationItemSelectedListener {
                return@setOnNavigationItemSelectedListener true
            }
            setBackgroundResource(R.color.black)
            selectedItemId = R.id.action_about
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.action_mode -> {
                        findNavController().navigate(R.id.action_aboutPage_to_modes)
                    }
                    R.id.action_light -> {
                        findNavController().navigate(R.id.action_aboutPage_to_static1)
                    }
                }
                return@setOnNavigationItemSelectedListener true
            }
        }

        return view
    }

}