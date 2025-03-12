package com.example.microstationapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.microstationapp.data.models.MeterModel

@Entity(tableName = "meter")
data class MeterEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int = 1,
    val meterType: String = "Analog",
    val currentMeter:Double = 0.0
)

fun MeterEntity.toMeterModel() = MeterModel(
    meterType = meterType,
    currentMeter = currentMeter
)

fun MeterModel.toEntity() = MeterEntity(
    meterType = meterType,
    currentMeter = currentMeter
)
