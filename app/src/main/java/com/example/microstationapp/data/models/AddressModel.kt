package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("coordinates")
    val coordinates: CoordinatesModel?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("postalCode")
    val postalCode: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("stateCode")
    val stateCode: String?
)