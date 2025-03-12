package com.example.microstationapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MeterEntity::class, NewSaleEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meterDao() : MeterDao
    abstract fun salesDao() : SalesDao
}