package com.example.microstationapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SalePeriodDao {
    @Query("SELECT * FROM sale_period WHERE isSaleOpen = 1 LIMIT 1")
    suspend fun getOpenSalePeriod(): SalePeriodEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalePeriod(salePeriod: SalePeriodEntity)

    @Query("UPDATE sale_period SET isSaleOpen = 0, endTime = :endTime WHERE id = :id")
    suspend fun closeSalePeriod(id:Int, endTime:Long)
}