package com.example.yeelightapp.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampForUI
import com.example.yeelightapp.ui.fragments.ListFragments


class ListAdapter(listFragments: ListFragments) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var lampList = arrayListOf<LampForUI>()
    private val listFragment = listFragments

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_lamp, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLamp = lampList[position]
        listFragment.processTheList(holder, currentLamp)

    }

    fun setData(lamps: ArrayList<LampForUI>) {
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