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

    private val args: AboutPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_page, container, false)
    }

    override fun onResume() {
        super.onResume()

        val ip: String = args.IP

        val navigationBottom: BottomNavigationView =
            requireView().findViewById(R.id.bottomNavigationAbout)

        navigationBottom.selectedItemId = R.id.action_about
        navigationBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_mode -> {
                    findNavController().navigate(AboutPageDirections.actionAboutPageToModes(ip))
                }
                R.id.action_light -> {
                    findNavController().navigate(AboutPageDirections.actionAboutPageToStatic1(ip))
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}