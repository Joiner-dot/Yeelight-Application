package com.example.yeelightapp.mapper

import androidx.lifecycle.LiveData

interface Mapper<SRC, DST> {
    fun transform(data: LiveData<List<SRC>>): LiveData<ArrayList<DST>>

    fun reverseTransform(data: DST): SRC
}