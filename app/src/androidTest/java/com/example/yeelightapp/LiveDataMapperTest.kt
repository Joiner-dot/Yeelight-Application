package com.example.yeelightapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yeelightapp.lamps.LampDB
import com.example.yeelightapp.lamps.LampUI
import com.example.yeelightapp.mapper.LampMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class LiveDataMapperTest {

    private val mapper = LampMapper()

    private val expected = MutableLiveData<List<LampUI>>()

    private val dataDB = MutableLiveData<List<LampDB>>()

    private val emptyExpected = MutableLiveData<List<LampUI>>()

    private val emptyDataDB = MutableLiveData<List<LampDB>>()

    private val nullExpected = null

    private val nullDataDB = null

    @Before
    fun fillIn() {
        expected.postValue(
            listOf(
                LampUI(1, "Room", "14.08.2000"),
                LampUI(2, "Kitchen", "192.168.1.45"),
                LampUI(3, "Shower", "123456789"),
                LampUI(4, "Another Room", "14.08.2000"),
                LampUI(5, "Room", "14.08.2000")

            )
        )
        dataDB.postValue(
            listOf(
                LampDB(1, "Room", "14.08.2000"),
                LampDB(2, "Kitchen", "192.168.1.45"),
                LampDB(3, "Shower", "123456789"),
                LampDB(4, "Another Room", "14.08.2000"),
                LampDB(5, "Room", "14.08.2000")
            )
        )
    }

    @Test
    fun fromLampDBToLampUITest() {
        val result = mapper.transform(dataDB)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertEquals(expected.value, value)
                }
            }
        }
    }

    @Test
    fun emptyListTest() {
        val result = mapper.transform(emptyDataDB)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertEquals(emptyExpected.value, value)
                }
            }
        }
    }

    @Test
    fun emptyLampDBListTest() {
        val result = mapper.transform(emptyDataDB)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertNotEquals(expected.value, value)
                }
            }
        }
    }

    @Test
    fun emptyLampUIListTest() {
        val result = mapper.transform(dataDB)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertNotEquals(emptyExpected.value, value)
                }
            }
        }
    }

    @Test
    fun nullExpectedTest() {
        val result = mapper.transform(dataDB)
        runBlocking {
            launch(Dispatchers.Main) {
                result.observeForever { value ->
                    assertNotEquals(nullExpected, value)
                }
            }
        }
    }
}