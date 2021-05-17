package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class AboutPage : Fragment() {

    private val args: Bundle by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_page, container, false)
    }

    override fun onResume() {
        super.onResume()
        val ip: String = this.arguments?.getString("IP").toString()
        val navigationBottom: BottomNavigationView =
            requireView().findViewById(R.id.bottomNavigationAbout)
        navigationBottom.selectedItemId = R.id.action_about
        navigationBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_mode -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_aboutPage_to_modes, args)
                }
                R.id.action_light -> {
                    args.putString("IP", ip)
                    findNavController().navigate(R.id.action_aboutPage_to_static1, args)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}