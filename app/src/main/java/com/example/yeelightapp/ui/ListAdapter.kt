package com.example.yeelightapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampDst
import com.example.yeelightapp.ui.viewmodel.LampViewModel


class ListAdapter(viewModel: ViewModel, context: Context) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var lampList = arrayListOf<LampDst>()

    private val lampViewModel = viewModel

    private val contextAdapter = context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_lamp, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLamp = lampList[position]
        val name = holder.itemView.findViewById<TextView>(R.id.lampName)
        val ip = holder.itemView.findViewById<TextView>(R.id.ipLamp)
        ip.text = currentLamp.ip
        name.text = currentLamp.name
        name.isClickable = true
        name.setOnClickListener {
            if (lampViewModel is LampViewModel) {
                val e = lampViewModel.connect(currentLamp.ip)
                e.observeForever { returned ->
                    if (!returned) {
                        Toast.makeText(
                            contextAdapter,
                            "Connection failed",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val args = Bundle()
                        args.putString("IP", currentLamp.ip)
                        args.putString("NAME", currentLamp.name)
                        Navigation.createNavigateOnClickListener(R.id.action_listFragments_to_mainMenu, args)
                            .onClick(name)
                    }
                }
            }
        }
        name.setOnLongClickListener {
            try {
                if (lampViewModel is LampViewModel) {
                    lampViewModel.deleteLamp(name.text.toString(), ip.text.toString())
                }
            } catch (e: Exception) {
                Log.d("Exception", e.printStackTrace().toString())
            }
            return@setOnLongClickListener true
        }
    }

    fun setData(lamps: ArrayList<LampDst>) {
        var flag = false
        val index: Int
        if (lampList.size > lamps.size) {
            for (it in 0 until lamps.size) {
                if (lamps[it].name != lampList[it].name || lamps[it].ip != lampList[it].ip) {
                    lampList.removeAt(it)
                    notifyItemRemoved(it)
                    flag = true
                }
            }
            if (!flag || lamps.size == 0) {
                index = lampList.size - 1
                lampList.removeAt(index)
                notifyItemRemoved(index)
            }
        } else {
            this.lampList.clear()
            lampList.addAll(lamps)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return lampList.size
    }
}