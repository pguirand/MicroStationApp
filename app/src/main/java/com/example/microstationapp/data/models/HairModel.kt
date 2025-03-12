package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class HairModel(
    @SerializedName("color")
    val color: String?,
    @SerializedName("type")
    val type: String?
)