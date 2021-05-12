package com.example.yeelightapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R

class MainMenu : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.main_menu, container, false)
        val staticButton:Button = view.findViewById(R.id.staticbutton)
        val modesButton:Button = view.findViewById(R.id.modesButton)
        val welcomeMainMenu:TextView = view.findViewById(R.id.welcomeMainMenu)
        val ip = this.arguments?.getString("IP").toString()
        val name = this.arguments?.getString("NAME").toString()
        welcomeMainMenu.text = "Welcome to the $name"

        staticButton.setOnClickListener {
            val args = Bundle()
            args.putString("IP", ip)
            findNavController().navigate(R.id.action_mainMenu_to_static1, args)
        }

        modesButton.setOnClickListener {
            val args = Bundle()
            args.putString("IP", ip)
            findNavController().navigate(R.id.action_mainMenu_to_modes, args)
        }

        return view
    }

}