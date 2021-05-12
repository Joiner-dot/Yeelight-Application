package com.example.yeelightapp.data.dao.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.lamps.LampFromDB

@Dao
interface LampDB : DataBase {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertNewLamp(lamp: LampFromDB)

    @Query("SELECT * FROM lamps")
    override fun selectAllLamps(): LiveData<List<LampFromDB>>

    @Query("DELETE FROM lamps WHERE id = :id")
    override suspend fun deleteByNameAndIp(id:Int)
}