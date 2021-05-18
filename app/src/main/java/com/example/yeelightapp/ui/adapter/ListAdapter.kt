package com.example.yeelightapp.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.ui.fragments.ListFragments


class ListAdapter(listFragments: ListFragments) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var lampList = arrayListOf<LampUI>()

    private val listFragment = listFragments


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_lamp, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLamp = lampList[position]
        listFragment.processTheList(holder, currentLamp, lampList[0])

    }

    fun setData(lamps: List<LampUI>) {

        var flag = false

        val index: Int


        if (lampList.size > lamps.size) {
            for (it in lamps.indices) {
                if (lamps[it].name != lampList[it].name || lamps[it].ip != lampList[it].ip) {
                    lampList.removeAt(it)
                    notifyItemRemoved(it)
                    flag = true
                }
            }
            if (!flag || lamps.isEmpty()) {
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