package com.example.microstationapp.data.models


import com.google.gson.annotations.SerializedName

data class AllUsersModel(
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("skip")
    val skip: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("users")
    val users: List<UserModel>?
)