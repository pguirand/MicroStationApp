package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class BankModel(
    @SerializedName("cardExpire")
    val cardExpire: String?,
    @SerializedName("cardNumber")
    val cardNumber: String?,
    @SerializedName("cardType")
    val cardType: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("iban")
    val iban: String?
)