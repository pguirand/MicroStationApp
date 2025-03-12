package com.example.microstationapp.data.room

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `sales` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `product` TEXT NOT NULL,
                `unitPrice` REAL NOT NULL,
                `quantity` REAL NOT NULL,
                `amount` REAL NOT NULL,
                `meterBeforeSale` REAL NOT NULL,
                `meterAfterSale` REAL NOT NULL,
                `paymentMethod` TEXT NOT NULL,
                `timestamp` INTEGER NOT NULL
            )
            """
        )
    }

}