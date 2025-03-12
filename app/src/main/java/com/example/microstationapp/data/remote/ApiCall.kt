package com.example.microstationapp.data.remote

import com.example.microstationapp.common.ApiDetails
import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.AllUsersModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.models.UserModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCall {

    @GET(ApiDetails.ALL_PRODUCTS)
    suspend fun getAllProducts() : AllProductsModel

    @GET(ApiDetails.SINGLE_PRODUCT)
    suspend fun getProductById(@Path("id") id:String) : ProductModel

    @GET(ApiDetails.ALL_USERS)
    suspend fun getUsers() : AllUsersModel

    @GET(ApiDetails.SINGLE_USER)
    suspend fun getUserById(@Path("id") id: String) : UserModel

}