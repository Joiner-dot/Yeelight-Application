package com.example.yeelightapp.database.dao.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yeelightapp.database.dao.DataBase
import com.example.yeelightapp.database.datasource.Lamp

@Dao
interface LampImpl: DataBase {

    @Insert(onConflict = OnConflictStrategy.ABORT)
   override suspend fun insertNewLamp(lamp: Lamp)

    @Delete
   override suspend fun deleteLamp(lamp: Lamp)

    @Query("SELECT * FROM lamps")
   override fun selectAllLamps(): LiveData<List<Lamp>>
}