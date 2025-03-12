package com.example.microstationapp.data.repository

import com.example.microstationapp.data.models.AllProductsModel
import com.example.microstationapp.data.models.AllUsersModel
import com.example.microstationapp.data.models.ProductModel
import com.example.microstationapp.data.models.UserModel

interface Repository {
    suspend fun getAllProducts() : AllProductsModel

    suspend fun getProductById(id:String) : ProductModel

    suspend fun getUsers() : AllUsersModel

    suspend fun getUserById(id:String) : UserModel
}