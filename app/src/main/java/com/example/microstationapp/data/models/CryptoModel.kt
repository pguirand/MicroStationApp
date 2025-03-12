package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class CryptoModel(
    @SerializedName("coin")
    val coin: String?,
    @SerializedName("network")
    val network: String?,
    @SerializedName("wallet")
    val wallet: String?
)