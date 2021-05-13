package com.example.yeelightapp.mapper

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.yeelightapp.lamps.LampForUI
import com.example.yeelightapp.lamps.LampFromDB

class LampMapper {
    fun transform(data: LiveData<List<LampFromDB>>): LiveData<ArrayList<LampForUI>> {
        return Transformations.map(data,
            Function {
                val listResults = arrayListOf<LampForUI>()
                listResults.clear()
                for (i in it) {
                    listResults.add(LampForUI(i.id, i.name, i.ip))
                }
                return@Function listResults
            })
    }

    fun reverseTransform(data: LampForUI): LampFromDB = LampFromDB(0, data.name, data.ip)
}