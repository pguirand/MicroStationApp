package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class CoordinatesModel(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lng")
    val lng: Double?
)