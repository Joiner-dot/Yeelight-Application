package com.example.yeelightapp.data.dao.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.lamps.LampDB

@Dao
interface LampDAO : DataBase {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertNewLamp(lamp: LampDB)

    @Query("SELECT * FROM lamps")
    override fun selectAllLamps(): LiveData<List<LampDB>>

    @Delete
    override suspend fun deleteLamp(lamp: LampDB)
}