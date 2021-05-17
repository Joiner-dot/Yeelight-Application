package com.example.yeelightapp.mapper

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.lamps.LampDB

class LampMapper {
    fun transform(data: LiveData<List<LampDB>>): LiveData<List<LampUI>> {
        return Transformations.map(data,
            Function {
                val listResults = arrayListOf<LampUI>()
                listResults.clear()
                for (i in it) {
                    listResults.add(LampUI(i.id, i.name, i.ip))
                }
                return@Function listResults
            })
    }

    fun reverseTransform(data: LampUI): LampDB = LampDB(data.id, data.name, data.ip)
}