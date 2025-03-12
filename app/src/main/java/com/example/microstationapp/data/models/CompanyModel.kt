package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class CompanyModel(
    @SerializedName("address")
    val address: AddressModel?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("title")
    val title: String?
)