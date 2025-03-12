package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class DimensionsModel(
    @SerializedName("depth")
    val depth: Double?,
    @SerializedName("height")
    val height: Double?,
    @SerializedName("width")
    val width: Double?
)