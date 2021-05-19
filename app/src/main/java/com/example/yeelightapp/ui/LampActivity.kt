package com.example.yeelightapp.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.yeelightapp.R
import com.example.yeelightapp.ui.viewmodel.LampViewModel
import org.koin.android.ext.android.inject

class LampActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lamp_activity)
    }
}