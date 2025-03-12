package com.example.microstationapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.microstationapp.data.models.NewSaleModel

@Entity(tableName = "sales")
data class NewSaleEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val product:String,
    val unitPrice : Double,
    val quantity: Double,
    val amount:Double,
    val meterBeforeSale:Double,
    val meterAfterSale:Double,
    val paymentMethod:String,
    val timestamp:Long
    )

fun NewSaleEntity.toModel() = NewSaleModel(
    product = product,
    unitPrice = unitPrice,
    quantity = quantity,
    amount = amount,
    meterBeforeSale = meterBeforeSale,
    meterAfterSale = meterAfterSale,
    paymentMethod = paymentMethod,
    timestamp = timestamp
)

fun NewSaleModel.toEntity() = NewSaleEntity(
    product = product,
    unitPrice = unitPrice,
    quantity = quantity,
    amount = amount,
    meterBeforeSale = meterBeforeSale,
    meterAfterSale = meterAfterSale,
    paymentMethod = paymentMethod,
    timestamp = timestamp
)