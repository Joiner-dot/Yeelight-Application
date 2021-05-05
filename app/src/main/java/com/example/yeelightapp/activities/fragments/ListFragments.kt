package com.example.yeelightapp.activities.fragments

import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.activities.MenuPage
import com.example.yeelightapp.database.interfaces.Lamp
import com.example.yeelightapp.database.interfaces.LampViewModel
import com.example.yeelightapp.database.interfaces.ListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragments : Fragment() {

    private lateinit var mLampViewModel: LampViewModel

    val params: LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        params.setMargins(0, 50, 0, 0)
        val view = inflater.inflate(R.layout.list_fragments, container, false)
        val recycle: LinearLayout = view.findViewById(R.id.listofLamps)
        val button: FloatingActionButton = view.findViewById(R.id.addButton)
        mLampViewModel = ViewModelProvider(this).get(LampViewModel::class.java)
        mLampViewModel.readAllData.observe(viewLifecycleOwner, { lamps ->
            for (i in lamps.indices) {
                val textView = TextView(view.context)
                textView.text = lamps[i].name
                textView.isClickable = true
                textView.textSize = 26f
                textView.layoutParams = params
                textView.gravity = Gravity.CENTER
                textView.setTextColor(Color.parseColor("#000000"))
                textView.setPadding(10, 10, 10, 10)
                textView.background = ContextCompat.getDrawable(view.context, R.drawable.border)
                textView.setOnClickListener {
                    val intent = Intent(view.context, MenuPage::class.java)
                    intent.putExtra("IP", lamps[i].ip)
                    startActivity(intent)
                }
                recycle.addView(textView)
                textView.setOnLongClickListener {
                    val lamp = Lamp(lamps[i].id, lamps[i].name, lamps[i].ip)
                    mLampViewModel.deleteLamp(lamp)
                    findNavController().navigate(R.id.action_listFragments_self)
                    return@setOnLongClickListener true
                }
            }
        })

        button.setOnClickListener {
            findNavController().navigate(R.id.action_listFragments_to_addLamp)
        }
        return view
    }

}