package com.example.yeelightapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampDst


class ListAdapter() : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var lampList = emptyList<LampDst>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

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
            val args = Bundle()
            args.putString("IP", currentLamp.ip)
            Navigation.createNavigateOnClickListener(R.id.action_listFragments_to_mainMenu, args)
                .onClick(name)
        }
    }

    fun setData(lamps: List<LampDst>) {
        this.lampList = lamps
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lampList.size
    }
}