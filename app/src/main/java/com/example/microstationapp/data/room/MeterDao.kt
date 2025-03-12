package com.example.microstationapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeterDao {
    @Query("SELECT * FROM meter WHERE id = 1")
    suspend fun getCurrentMeter() : MeterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentMeter(meter:MeterEntity)
}