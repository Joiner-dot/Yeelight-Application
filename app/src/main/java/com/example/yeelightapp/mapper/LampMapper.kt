package com.example.yeelightapp.mapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yeelightapp.lamps.LampDst
import com.example.yeelightapp.lamps.LampSrc

class LampMapper : Mapper<LampSrc, LampDst> {
    override fun transform(data: LiveData<List<LampSrc>>): LiveData<ArrayList<LampDst>> {
        val list = arrayListOf<LampDst>()
        val values = MutableLiveData<ArrayList<LampDst>>()
        data.observeForever {
            list.clear()
            for (i in it) {
                list.add(LampDst(i.name, i.ip))
            }
            values.postValue(list)
        }
        return values
    }

    override fun reverseTransform(data: LampDst): LampSrc = LampSrc(0, data.name, data.ip)
}