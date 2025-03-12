package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class AllProductsModel(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("products")
    val products: List<ProductModel?>?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?
)