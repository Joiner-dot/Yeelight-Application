package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.yeelightapp.R

class MainMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.main_menu, container, false)
        val staticButton:Button = view.findViewById(R.id.staticButton)
        val bundle = this.arguments
        val ip = bundle?.getString("IP").toString()
        staticButton.setOnClickListener {
            val args = Bundle()
            args.putString("IP", ip)
            findNavController().navigate(R.id.action_mainMenu_to_static1, args)
        }
        return view
    }

}