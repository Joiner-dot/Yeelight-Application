package com.example.yeelightapp.database.interfaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var lampList = emptyList<Lamp>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lamp_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = lampList[position]
        val name:TextView = holder.itemView.findViewById(R.id.lampRow)
        name.text = currentItem.name + " - " + currentItem.ip
    }

    override fun getItemCount(): Int {
       return lampList.size
    }


    fun setData (lamps:List<Lamp>){
        this.lampList = lamps
        notifyDataSetChanged()
    }
}