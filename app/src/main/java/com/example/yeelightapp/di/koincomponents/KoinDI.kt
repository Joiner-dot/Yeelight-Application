package com.example.yeelightapp.di.koincomponents

import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class KoinDI:KoinComponent {

    val gson: Gson by inject()
}
