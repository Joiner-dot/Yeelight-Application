package com.example.yeelightapp.ui.fragments

import android.app.ActionBar
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.di.appModule
import com.example.yeelightapp.lamps.LampDst
import com.example.yeelightapp.ui.ListAdapter
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.get


class ListFragments : Fragment() {

    private lateinit var mLampViewModel: LampViewModel
    private val params: LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
        )

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        params.setMargins(0, 50, 0, 0)
        val view = inflater.inflate(R.layout.list_fragments, container, false)
        val recycle: RecyclerView = view.findViewById(R.id.recyclefrag)
        recycle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val button: FloatingActionButton = view.findViewById(R.id.addButton)
        mLampViewModel = get()
        val adapter = ListAdapter()
        recycle.adapter = adapter
        mLampViewModel.readAllData.observe(viewLifecycleOwner, { lamps ->
            for (i in lamps){
                Log.d("Taaag", i.name)
            }
            recycle.removeAllViews()
            adapter.setData(lamps)
        })
        button.setOnClickListener {
            findNavController().navigate(R.id.action_listFragments_to_addLamp)
        }
        return view
    }
}