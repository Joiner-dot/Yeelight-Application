package com.example.yeelightapp.mapper

import androidx.lifecycle.LiveData

interface Mapper<SRC, DST> {
    fun transform(data: LiveData<List<SRC>>): LiveData<List<DST>>

    fun reverseTransform(data: DST): SRC
}