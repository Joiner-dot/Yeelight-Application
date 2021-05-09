package com.example.yeelightapp.mapper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yeelightapp.lamps.LampDst
import com.example.yeelightapp.lamps.LampSrc

class LampMapper : Mapper<LampSrc, LampDst> {
    override fun transform(data: LiveData<List<LampSrc>>): LiveData<List<LampDst>> {
        val list = arrayListOf<LampDst>()
        val values = MutableLiveData<List<LampDst>>()
        data.observeForever {
            for (i in it) {
                list.add(LampDst(i.name, i.ip))
            }
            Log.d("SSS", list.size.toString())
            values.postValue(list)
        }
        return values
    }

    override fun reverseTransform(data: LampDst): LampSrc = LampSrc(0, data.name, data.ip)
}