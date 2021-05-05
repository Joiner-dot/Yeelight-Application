package com.example.yeelightapp.database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IDataBase {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNewLamp(lamp: Lamp)

    @Delete
    suspend fun deleteLamp(lamp: Lamp)

    @Query("SELECT * FROM lamps")
    fun selectAllLamps(): LiveData<List<Lamp>>
}