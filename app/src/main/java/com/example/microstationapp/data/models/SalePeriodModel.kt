package com.example.microstationapp.data.models

data class SalePeriodModel(
    val meterModel: MeterModel = MeterModel(),
    var totalSalesAmount: Double = 0.0,              // Cumulative total sales amount
    var totalGallonsSold: Double = 0.0,             // Cumulative total gallons sold
    val sales: MutableList<NewSaleModel> = mutableListOf(), // List
    val startTime: Long = System.currentTimeMillis(), // Open sales period timestamp
    var endTime: Long? = null, // Cl
    val isSaleOpen: Boolean = false
)

