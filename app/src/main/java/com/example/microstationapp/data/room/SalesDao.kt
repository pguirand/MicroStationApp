package com.example.microstationapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SalesDao {
    @Query("SELECT * FROM sales")
    suspend fun getAllSales() : List<NewSaleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale:NewSaleEntity)

    @Query("DELETE FROM sales")
    suspend fun clearSales()
}