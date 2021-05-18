package com.example.yeelightapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.ui.adapter.ListAdapter
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragments : Fragment() {

    private val mLampViewModel: LampViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.list_fragments, container, false)

        val recycle: RecyclerView = view.findViewById(R.id.recyclefrag)

        val floatingActionButton: FloatingActionButton = view.findViewById(R.id.addButton)

        val adapter = ListAdapter(this)

        recycle.adapter = adapter
        recycle.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        mLampViewModel.readAllData.observe(viewLifecycleOwner, { lamps ->
            adapter.setData(lamps)
        })
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragments_to_addLamp)
        }
        return view
    }

    fun processTheList(
        holder: ListAdapter.MyViewHolder,
        currentLamp: LampUI,
        firstLamp: LampUI
    ) {

        val nameForRow = holder.itemView.findViewById<TextView>(R.id.lampName)

        val ipForRow = holder.itemView.findViewById<TextView>(R.id.ipLamp)

        val lampIconForRow = holder.itemView.findViewById<ImageView>(R.id.lampRow)


        lampIconForRow.setImageResource(R.drawable.list_lamp)
        ipForRow.text = currentLamp.ip
        nameForRow.text = currentLamp.name
        nameForRow.isClickable = true
        nameForRow.setOnClickListener {
            val connectValue = mLampViewModel.connect(currentLamp.ip)
            connectValue.observe(this, { returned ->
                if (!returned) {
                    Toast.makeText(
                        requireContext(),
                        "Connection failed",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(
                        ListFragmentsDirections.actionListFragmentsToModes(currentLamp.ip)
                    )
                }
            })
        }
        nameForRow.setOnLongClickListener {

            val result = mLampViewModel.deleteLamp(currentLamp)


            result.observe(this, { value ->
                if (value) {
                    Toast.makeText(
                        requireContext(),
                        "Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to Delete",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_listFragments_self)
                }
            })
            return@setOnLongClickListener true
        }
        if (firstLamp.id == currentLamp.id) {
            nameForRow.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.border_first_row)
        }
    }
}
