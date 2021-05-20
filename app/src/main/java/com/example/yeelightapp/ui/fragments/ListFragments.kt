package com.example.yeelightapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeelightapp.R
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.ui.LampActivity
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

        return view
    }

    override fun onResume() {
        super.onResume()

        val recycle: RecyclerView = requireView().findViewById(R.id.recyclefrag)

        val floatingActionButton: FloatingActionButton = requireView().findViewById(R.id.addButton)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        mLampViewModel.closeConnection()
    }

    fun processTheList(
        holder: ListAdapter.MyViewHolder,
        currentLamp: LampUI,
        firstLamp: LampUI
    ) {

        val nameForRow = holder.itemView.findViewById<TextView>(R.id.lampName)

        val ipForRow = holder.itemView.findViewById<TextView>(R.id.ipLamp)

        val lampIconForRow = holder.itemView.findViewById<ImageView>(R.id.lampRow)

        val progressOfOperation: ProgressBar = requireActivity().findViewById(R.id.progressBar)

        val powerLamp: Switch = holder.itemView.findViewById(R.id.powerList)

        val connectionValue = mLampViewModel.connect(currentLamp.ip, 0)


        connectionValue.observe(viewLifecycleOwner, { value ->
            if (value) {
                val currentProps = mLampViewModel.setCurrentRGBB(currentLamp.ip, 0)

                currentProps.observe(viewLifecycleOwner, { propValue ->
                    if (propValue.power == "on") {
                        lampIconForRow.setImageResource(R.drawable.on_lamp)
                        turnSwitch(powerLamp, true, lampIconForRow)
                    } else {
                        lampIconForRow.setImageResource(R.drawable.list_lamp)
                        turnSwitch(powerLamp, false, lampIconForRow)
                    }
                })
            } else {
                disEnableLamp(powerLamp, lampIconForRow)
            }
        })
        ipForRow.text = currentLamp.ip
        nameForRow.apply {
            text = currentLamp.name
            isClickable = true
        }
        nameForRow.setOnClickListener {
            progressOfOperation.visibility = View.VISIBLE

            val connectValue = mLampViewModel.connect(currentLamp.ip, 0)


            connectValue.observe(this, { returned ->
                progressOfOperation.visibility = View.INVISIBLE
                if (!returned) {
                    Toast.makeText(
                        requireContext(),
                        "Connection failed",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent = Intent(activity, LampActivity::class.java)
                    intent.putExtra("IP", currentLamp.ip)
                    startActivity(intent)
                }
            })
        }

        nameForRow.setOnLongClickListener {

            progressOfOperation.visibility = View.VISIBLE
            val result = mLampViewModel.deleteLamp(currentLamp)


            result.observe(this, { value ->
                progressOfOperation.visibility = View.INVISIBLE
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

    private fun disEnableLamp(onOff: Switch, image: ImageView) {
        onOff.isEnabled = false
        image.setImageResource(R.drawable.list_lamp)
    }

    private fun turnSwitch(onOff: Switch, value: Boolean, image: ImageView) {
        onOff.apply {
            setOnCheckedChangeListener(null)
            isChecked = value
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    image.setImageResource(R.drawable.on_lamp)
                    mLampViewModel.turnOn()
                } else {
                    image.setImageResource(R.drawable.list_lamp)
                    mLampViewModel.turnOff()
                }
            }
        }
    }
}
