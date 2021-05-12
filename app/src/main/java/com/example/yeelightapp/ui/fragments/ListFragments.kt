package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampForUI
import com.example.yeelightapp.ui.ListAdapter
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject


class ListFragments : Fragment() {

    private val mLampViewModel: LampViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.list_fragments, container, false)
        val recycle: RecyclerView = view.findViewById(R.id.recyclefrag)
        val button: FloatingActionButton = view.findViewById(R.id.addButton)
        val adapter = ListAdapter(this)
        recycle.adapter = adapter
        recycle.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        mLampViewModel.readAllData.observe(viewLifecycleOwner, { lamps ->
            adapter.setData(lamps)
        })
        button.setOnClickListener {
            findNavController().navigate(R.id.action_listFragments_to_addLamp)
        }
        return view
    }

    fun processTheList(holder: ListAdapter.MyViewHolder, currentLamp: LampForUI) {
        val name = holder.itemView.findViewById<TextView>(R.id.lampName)
        val ip = holder.itemView.findViewById<TextView>(R.id.ipLamp)
        ip.text = currentLamp.ip
        name.text = currentLamp.name
        name.isClickable = true
        name.setOnClickListener {
            val e = mLampViewModel.connect(currentLamp.ip)
            e.observe(this, { returned ->
                if (!returned) {
                    Toast.makeText(
                        requireContext(),
                        "Connection failed",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val args = Bundle()
                    args.putString("IP", currentLamp.ip)
                    args.putString("NAME", currentLamp.name)
                    findNavController().navigate(
                        R.id.action_listFragments_to_mainMenu,
                        args
                    )
                }
            })
        }
        name.setOnLongClickListener {
            try {
                mLampViewModel.deleteLamp(currentLamp.id)
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
            }
            return@setOnLongClickListener true
        }
    }
}
