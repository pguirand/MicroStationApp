package com.example.microstationapp.data.models

data class NewSaleModel(
    val product: String = "",
    val productCode: String = "",
    val unitPrice: Double = 0.0,
    var quantity: Double = 0.0,
    var amount: Double = 0.0,
    var discount: Double = 0.0,
    var tax: Double = 0.0,
    var finalAmount: Double = 0.0,
    val unitType: String = "gallon",
    val meterBeforeSale: Double = 0.0,
    var meterAfterSale: Double = 0.0,
    val transactionId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val salesperson: String = "",
    val paymentMethod: String = "Cash",
    val customerName: String = "",
    val customerId: String = "",
    val status: String = "Pending",
    val isRefundable: Boolean = true,
    val notes: String = ""
)


