package com.example.yeelightapp

import androidx.lifecycle.MutableLiveData
import com.example.yeelightapp.lamps.LampDB
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.mapper.LampMapper
import org.junit.Assert.*
import org.junit.Test


class LampMapperTest {

    private val uiData = LampUI(1, "Room", "14.08.2000")

    private val dbData = LampDB(1, "Room", "14.08.2000")

    private val emptyUiData = LampUI(1, "", "")

    private val emptyDbData = LampDB(1, "", "")

    private val nullDbData = null

    private val mapper = LampMapper()


    @Test
    fun fromLampUIToLampDBTest() {
        val result = mapper.reverseTransform(uiData)
        assertEquals(dbData, result)
    }

    @Test
    fun fromEmptyLampUIToEmptyLampDBTest() {
        val result = mapper.reverseTransform(emptyUiData)
        assertEquals(emptyDbData, result)
    }

    @Test
    fun fromLampUIToEmptyLampDBTest() {
        val result = mapper.reverseTransform(uiData)
        assertNotEquals(emptyDbData, result)
    }

    @Test
    fun fromEmptyLampUIToLampDBTest() {
        val result = mapper.reverseTransform(emptyUiData)
        assertNotEquals(dbData, result)
    }

    @Test
    fun fromLampUIToNullLampDBTest() {
        val result = mapper.reverseTransform(uiData)
        assertNotEquals(nullDbData, result)
    }
}