package com.example.yeelightapp.data.dao.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yeelightapp.data.dao.DataBase
import com.example.yeelightapp.lamps.LampSrc

@Dao
interface LampDB: DataBase {

    @Insert(onConflict = OnConflictStrategy.ABORT)
   override suspend fun insertNewLamp(lamp: LampSrc)

    @Delete
   override suspend fun deleteLamp(lamp: LampSrc)

    @Query("SELECT * FROM lamps")
   override fun selectAllLamps(): LiveData<List<LampSrc>>
}