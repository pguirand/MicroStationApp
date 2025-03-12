package com.example.microstationapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.microstationapp.data.models.SalePeriodModel

@Entity(tableName = "sale_period")
data class SalePeriodEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val totalSalesAmount: Double = 0.0,
    val totalGallonsSold: Double = 0.0,
    val startTime:Long,
    val endTime:Long? = null,
    val isSaleOpen:Boolean = false
)

fun SalePeriodEntity.toModel() = SalePeriodModel(
    totalSalesAmount = totalSalesAmount,
    totalGallonsSold = totalGallonsSold,
    startTime = startTime,
    endTime = endTime,
    isSaleOpen = isSaleOpen
)


fun SalePeriodModel.toEntity() = SalePeriodEntity(
    totalSalesAmount = totalSalesAmount,
    totalGallonsSold = totalGallonsSold,
    startTime = startTime,
    endTime = endTime,
    isSaleOpen = isSaleOpen
)