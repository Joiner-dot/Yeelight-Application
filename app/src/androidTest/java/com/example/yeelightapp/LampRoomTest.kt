package com.example.yeelightapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.yeelightapp.data.dao.room.LampDAO
import com.example.yeelightapp.data.datasource.room.AppDataBase
import com.example.yeelightapp.lamps.LampDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.rules.TestRule
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class LampRoomTest {
    private lateinit var lampDao: LampDAO
    private lateinit var db: AppDataBase
    private val lampDb: LampDB = LampDB(1, "Room", "14.08.2000")
    private val listLampDB: List<LampDB> = listOf(
        LampDB(1, "Room", "14.08.2000"),
        LampDB(2, "Room", "14.08.2000")
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java
        ).build()
        lampDao = db.lampDAO()
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeLampToDB() {
        runBlocking {
            launch(Dispatchers.Default) {
                lampDao.insertNewLamp(lampDb)
                val someValue = lampDao.selectAllLamps().blockingObserve()
                assertEquals(lampDb, someValue!![0])
                //list of lamps
                lampDao.insertNewLamp(LampDB(2, "Room", "14.08.2000"))
                val newValue = lampDao.selectAllLamps().blockingObserve()
                assertEquals(listLampDB, newValue)
            }
        }
    }

    @Test
    fun deleteLampFromDB() {
        runBlocking {
            launch(Dispatchers.Default) {
                lampDao.insertNewLamp(lampDb)
                val someValue = lampDao.selectAllLamps().blockingObserve()
                assertEquals(lampDb, someValue!![0])
                //list of lamps
                lampDao.deleteLamp(1)
                val newValue = lampDao.selectAllLamps().blockingObserve()
                assertEquals(0, newValue!!.size)
            }
        }
    }
}